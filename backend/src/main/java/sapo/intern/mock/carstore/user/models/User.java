package sapo.intern.mock.carstore.user.models;

import jakarta.persistence.*;
import lombok.*;
import sapo.intern.mock.carstore.user.enums.UserRole;

import java.time.Instant;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Integer age;
    private String urlImage;
    private boolean firstLogin;
    private String resetPasswordToken;
    private Instant resetPasswordTokenExpiry;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
