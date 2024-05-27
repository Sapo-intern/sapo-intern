package sapo.intern.mock.carstore.user.controllers;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.user.dto.request.ChangePasswordRequest;
import sapo.intern.mock.carstore.user.dto.request.LoginRequest;
import sapo.intern.mock.carstore.user.dto.request.LogoutRequest;
import sapo.intern.mock.carstore.user.dto.request.UserCreateRequest;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;
import sapo.intern.mock.carstore.user.dto.response.LoginResponse;
import sapo.intern.mock.carstore.user.enums.UserRole;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.services.AuthService;

import java.text.ParseException;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    ApiResponse<User> createUser(@RequestBody @Valid UserCreateRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authService.createUser(request));

        return apiResponse;
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> authenticate(@RequestBody LoginRequest request){
        var result = authService.loginUser(request);
        return ApiResponse.<LoginResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        authService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword(), request.getConfirmNewPassword());
        apiResponse.setMessage("Thay đổi mật khẩu thành công");
        return apiResponse;
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        String token = authService.forgotPassword(email);
        apiResponse.setResult(token);
        apiResponse.setMessage("Link đặt lại mật khẩu đã được gửi tới gmail của bạn");
        return apiResponse;
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String token, @RequestParam String newPassword, @RequestParam String confirmNewPassword) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        authService.resetPassword(token, newPassword, confirmNewPassword);
        apiResponse.setMessage("Mật khẩu đã được đặt lại thành công.");
        return apiResponse;
    }

    @PostMapping("/logout-user")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/role")
    public UserRole[] getUserRoles() {
        return UserRole.values();
    }
}
