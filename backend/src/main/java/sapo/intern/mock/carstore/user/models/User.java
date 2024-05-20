package sapo.intern.mock.carstore.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.issue.models.Employee;
import sapo.intern.mock.carstore.user.enums.UserRole;
import sapo.intern.mock.carstore.user.repositories.UserRepo;

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
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
//    private Employee employee;
}
