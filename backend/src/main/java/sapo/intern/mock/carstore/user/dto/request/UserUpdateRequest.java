package sapo.intern.mock.carstore.user.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String username;
     String name;

     @Pattern(regexp="\\d{10}", message="Số điện thoại phải có 10 chữ số")
     String phone;
     String address;

     @Min(value = 0, message = "Tuổi phải lớn hơn hoặc bằng 0")
     @Max(value = 150, message = "Tuổi phải nhỏ hơn hoặc bằng 150")
     Integer age;
     File urlImage;
}
