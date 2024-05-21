package sapo.intern.mock.carstore.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.user.dto.request.ChangePasswordRequest;
import sapo.intern.mock.carstore.user.dto.request.LoginRequest;
import sapo.intern.mock.carstore.user.dto.request.UserCreateRequest;
import sapo.intern.mock.carstore.user.dto.request.UserUpdateRequest;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;
import sapo.intern.mock.carstore.user.dto.response.LoginResponse;
import sapo.intern.mock.carstore.user.dto.response.UserReponse;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")

    ApiResponse<User> createUser(@RequestBody @Valid UserCreateRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("User has been deleted")
                .build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String userId, @Valid @RequestBody UserUpdateRequest request){
        User user = userService.updateUser(userId, request);
        ApiResponse<User> reponse = ApiResponse.<User>builder()
                .message("Cập nhật dữ liệu thành công")
                .result(user)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @PostMapping("/log-in")
    ApiResponse<LoginResponse> authenticate(@RequestBody LoginRequest request){
        var result = userService.loginUser(request);
        return ApiResponse.<LoginResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword(), request.getConfirmNewPassword());
        apiResponse.setMessage("Password changed successfully");
        return apiResponse;
    }
}
