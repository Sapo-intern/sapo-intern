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
}

