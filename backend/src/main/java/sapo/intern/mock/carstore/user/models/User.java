package sapo.intern.mock.carstore.user.models;

import jakarta.persistence.*;
import lombok.*;
import sapo.intern.mock.carstore.issue.models.Employee;
import sapo.intern.mock.carstore.user.enums.UserRole;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

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
//    @Enumerated(EnumType.ORDINAL)
//    private UserRole role;
    private String username;
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

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
//    private Employee employee;
}
