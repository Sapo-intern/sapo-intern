package sapo.intern.mock.carstore.user.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.user.dto.request.UserCreateRequest;
import sapo.intern.mock.carstore.user.exception.AppException;
import sapo.intern.mock.carstore.user.exception.ErrorCode;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(UserCreateRequest request) {
        String tempPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        String encodedPassword = passwordEncoder.encode(tempPassword);

        User existingUser = userRepo.findByEmail(request.getEmail());
        if(existingUser != null)
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        emailService.sendEmail(request.getEmail(), "Thông tin đăng nhập", "Username: " + user.getUsername() + "\nPassword: " + tempPassword);
        return user;
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
}

