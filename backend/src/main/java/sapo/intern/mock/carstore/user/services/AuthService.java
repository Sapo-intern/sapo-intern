package sapo.intern.mock.carstore.user.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.AppException;
import sapo.intern.mock.carstore.global.exceptions.ErrorCode;
import sapo.intern.mock.carstore.user.dto.request.LoginRequest;
import sapo.intern.mock.carstore.user.dto.request.LogoutRequest;
import sapo.intern.mock.carstore.user.dto.request.RefreshRequest;
import sapo.intern.mock.carstore.user.dto.request.UserCreateRequest;
import sapo.intern.mock.carstore.user.dto.response.IntrospectReponse;
import sapo.intern.mock.carstore.user.dto.response.IntrospectRequest;
import sapo.intern.mock.carstore.user.dto.response.LoginResponse;
import sapo.intern.mock.carstore.user.enums.UserRole;
import sapo.intern.mock.carstore.user.models.InvalidatedToken;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.repositories.InvalidatedTokenRepository;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class AuthService {
    UserRepo userRepo;
    EmailService emailService;
    InvalidatedTokenRepository invalidatedTokenRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NonFinal
    protected static final String SIGNER_KEY = "6C0EMJcWMlSpbTNJdLYPBckN0V1qc3I1KKUWYfRj28qFn9SLItejksAE//K75Qde";

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION ;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public User createUser(UserCreateRequest request) {
        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String encodedPassword = passwordEncoder.encode(tempPassword);

        User existingUserByEmail = userRepo.findByEmail(request.getEmail());
        if (existingUserByEmail != null) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setFirstLogin(true);

        UserRole role = request.getRole() != null ? request.getRole() : UserRole.TECHNICIAN;
        user.setRole(role);

        userRepo.save(user);

        emailService.sendEmail(request.getEmail(), "Thông tin đăng nhập", "Chào mừng bạn đến với công ty của chúng tôi và dưới đây là thông tin đăng nhập vào hệ thống của công ty" + "\nEmail: " + user.getEmail() + "\nPassword: " + tempPassword);
        return user;
    }

    public LoginResponse loginUser(LoginRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        var token = generateToken(user.getEmail(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .age(user.getAge())
                .urlImage(user.getUrlImage())
                .firstLogin(user.isFirstLogin())
                .role(user.getRole())
                .build();
    }

    public void changePassword(String email, String oldPassword, String newPassword, String confirmNewPassword) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRMATION_FAILED);
        }

        if (!isValidPassword(newPassword)) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setFirstLogin(false);
        userRepo.save(user);
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasLower = false, hasUpper = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasLower && hasUpper && hasDigit;
    }

    public String forgotPassword(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        String token = UUID.randomUUID().toString();

        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(Instant.now().plus(1, ChronoUnit.HOURS));
        userRepo.save(user);

        String resetLink = "http://localhost:5173/resetpassword/" + token;

        emailService.sendEmail(user.getEmail(), "Đặt lại mật khẩu", "Bấm vào link này để có thể đặt lại mật khẩu của bạn: " + resetLink + "\n\nVui lòng đặt lại mật khẩu luôn link này có hiệu lực trong vòng 1 tiếng");
        return token;
    }

    public void resetPassword(String token, String newPassword, String confirmNewPassword) {
        User user = userRepo.findByResetPasswordToken(token);
        if (user == null || user.getResetPasswordTokenExpiry().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.INVALID_OR_EXPIRED_TOKEN);
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRMATION_FAILED);
        }

        if (!isValidPassword(newPassword)) {
            throw new AppException(ErrorCode.WEAK_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepo.save(user);
    }

    private String generateToken(String username, UserRole role) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("quý")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", role.name())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public void logout(LogoutRequest request) throws JOSEException, ParseException {
        try{
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        }catch(AppException exception){
            log.info("Token already expired");
        }
    }

    private SignedJWT verifyToken(String token,boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public IntrospectReponse introspectReponse(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try{
            verifyToken(token,false);
        }catch (AppException e){
            isValid = false;
        }

        return IntrospectReponse.builder()
                .valid(isValid)
                .build();
    }

    public LoginResponse refreshToken(RefreshRequest request)
            throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(),true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

//        var user = userRepo.findByEmail(username).orElseThrow(
//                () -> new AppException(ErrorCode.UNAUTHENTICATED)
//        );

        var user = userRepo.findByEmail(username);
        if (user == null) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }

        var token = generateToken(user.getEmail(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
