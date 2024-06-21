package sapo.intern.mock.carstore.issue.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private String servicesCode;
    private String description;
    private Double price;
    @JsonIgnore
    @OneToMany(mappedBy = "repairService", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void addIssue(Issue newIssue) {
        if (!issues.contains(newIssue)) {
            issues.add(newIssue);
            newIssue.setRepairService(this);
        }
    }


}
