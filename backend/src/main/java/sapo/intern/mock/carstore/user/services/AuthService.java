package sapo.intern.mock.carstore.user.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.user.dto.request.LoginRequest;
import sapo.intern.mock.carstore.user.dto.request.UserCreateRequest;
import sapo.intern.mock.carstore.user.dto.response.LoginResponse;
import sapo.intern.mock.carstore.user.exception.AppException;
import sapo.intern.mock.carstore.user.exception.ErrorCode;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class AuthService {
    UserRepo userRepo;
    EmailService emailService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NonFinal
    protected static final String SIGNER_KEY = "6C0EMJcWMlSpbTNJdLYPBckN0V1qc3I1KKUWYfRj28qFn9SLItejksAE//K75Qde";

    public User createUser(UserCreateRequest request) {
        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String encodedPassword = passwordEncoder.encode(tempPassword);

        User existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        emailService.sendEmail(request.getEmail(), "Thông tin đăng nhập", "Username: " + user.getUsername() + "\nPassword: " + tempPassword);
        return user;
    }

    public LoginResponse loginUser(LoginRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(),
                user.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.INVALID_PASSWORD);
//        var token = generateToken(user);
        var token = generateToken(request.getUsername());
        return LoginResponse.builder()
                .token(token)
                .authenticated(true)
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

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("quý")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("userId", "Custom")
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
}
