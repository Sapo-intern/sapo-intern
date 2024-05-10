package sapo.intern.mock.carstore.issue.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sapo.intern.mock.carstore.user.models.User;


@Table(name = "employees")
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "String must contain only alphanumeric characters")
    @Size(max = 30, message = "String must have a maximum of 30 characters")
    private String name;
    @Pattern(regexp = "\\d{9,11}", message = "String must contain 9 to 11 digits")
    private String phoneNumber;
    @Min(1)
    @Max(100)
    private int age;
    @OneToOne(mappedBy = "employee")
    private Issue issue;
    @OneToOne(mappedBy = "employee")
    private User user;
}
