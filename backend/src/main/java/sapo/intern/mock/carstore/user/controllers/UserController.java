package sapo.intern.mock.carstore.user.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.user.dto.request.UserUpdateRequest;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Page<User>> getAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<User> userPage = userService.getAllUser(page, size);
        return ResponseEntity.ok(userPage);
    }




    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getProduct(@PathVariable("userId") String userId){
        User user = userService.getUser(userId);

        ApiResponse<User> reponse = ApiResponse.<User>builder()
                .message("Lấy dữ liệu thành công")
                .result(user)
                .build();
        return ResponseEntity.ok(reponse);
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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUsersByNameOrPhoneNumber(@RequestParam String query) {
        List<User> user = userService.searchByNameOrPhoneNumber(query);
        ApiResponse<List<User>> response = ApiResponse.<List<User>>builder()
                .message("Danh sách người dùng được tìm thấy")
                .result(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("Bạn đã xóa thành công")
                .build();
    }
}
