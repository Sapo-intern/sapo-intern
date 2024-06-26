package sapo.intern.mock.carstore.user.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sapo.intern.mock.carstore.user.dto.request.UserUpdateRequest;
import sapo.intern.mock.carstore.user.dto.response.ApiResponse;
import sapo.intern.mock.carstore.user.models.User;
import sapo.intern.mock.carstore.user.services.UserService;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<Page<User>> getAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<User> userPage = userService.getAllUser(page, size);
        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<User>> getProduct(@PathVariable("userId") String userId) {
        User user = userService.getUser(userId);

        ApiResponse<User> reponse = ApiResponse.<User>builder()
                .message("Lấy dữ liệu thành công")
                .result(user)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String userId,
                                                        @Valid @ModelAttribute UserUpdateRequest request,
                                                        @RequestParam("imageFile") MultipartFile imageFile
                                                        ) {
        User user = userService.updateUser(userId, request, imageFile);
        ApiResponse<User> reponse = ApiResponse.<User>builder()
                .message("Cập nhật dữ liệu thành công")
                .result(user)
                .build();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> searchUsersByNameOrPhoneNumber(@RequestParam String keyword) {
        List<User> user = userService.getUserByKeyword(keyword);
        ApiResponse<List<User>> response = ApiResponse.<List<User>>builder()
                .message("Danh sách người dùng được tìm thấy")
                .result(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .result("Bạn đã xóa thành công")
                .build();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        String data = userService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
