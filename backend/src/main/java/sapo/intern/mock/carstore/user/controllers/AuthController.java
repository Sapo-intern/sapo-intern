package sapo.intern.mock.carstore.user.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.user.dto.request.ChangePasswordRequest;
import sapo.intern.mock.carstore.user.dto.request.LoginRequest;
import sapo.intern.mock.carstore.user.dto.request.UserCreateRequest;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;
import sapo.intern.mock.carstore.user.dto.response.LoginResponse;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.services.AuthService;

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

    @PostMapping("/log-in")
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
        apiResponse.setMessage("Password changed successfully");
        return apiResponse;
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        authService.forgotPassword(email);
        apiResponse.setMessage("Password reset link has been sent to your email.");
        return apiResponse;
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestParam String token, @RequestParam String newPassword, @RequestParam String confirmNewPassword) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        authService.resetPassword(token, newPassword, confirmNewPassword);
        apiResponse.setMessage("Password has been reset successfully.");
        return apiResponse;
    }
}
