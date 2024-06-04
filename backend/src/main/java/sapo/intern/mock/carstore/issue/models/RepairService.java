package sapo.intern.mock.carstore.issue.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RepairService {
    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String serviceCode;
    private String description;
    private Double price;
    @JsonIgnore
    @OneToMany(mappedBy = "repairService", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    public void addIssue(Issue newIssue) {
        if (!issues.contains(newIssue)) {
            issues.add(newIssue);
            newIssue.setRepairService(this);
        }
    }


}
