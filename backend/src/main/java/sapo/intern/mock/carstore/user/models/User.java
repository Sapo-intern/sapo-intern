package sapo.intern.mock.carstore.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import sapo.intern.mock.carstore.issue.models.Issue;
import sapo.intern.mock.carstore.user.enums.UserRole;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    public void addIssue(Issue foundIssue) {
        if (!issues.contains(foundIssue)){
            foundIssue.setUser(this);
            issues.add(foundIssue);
        }
    }

    public void removeIssue(Issue foundIssue) {
        foundIssue.setUser(null);
        issues.remove(foundIssue);
    }
}
