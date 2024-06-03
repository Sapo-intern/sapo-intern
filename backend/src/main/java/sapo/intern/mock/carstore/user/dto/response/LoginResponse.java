package sapo.intern.mock.carstore.user.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import sapo.intern.mock.carstore.user.enums.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String token;
    String refreshToken;
    Long id;
    String name;
    String phone;
    String email;
    String address;
    Integer age;
    String urlImage;
    boolean firstLogin;
    @Enumerated(EnumType.STRING)
    UserRole role;
}
