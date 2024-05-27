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
    private Long id;
    private String username;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Integer age;
    private String urlImage;
    private boolean firstLogin;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
