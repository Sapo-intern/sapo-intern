package sapo.intern.mock.carstore.user.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.user.enums.UserRole;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class UserCreateRequest {
    @Email(message = "EMAIL_INVALID")
    private String email;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
