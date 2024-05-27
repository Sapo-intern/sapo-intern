package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String description;
    private int price;
    private int totalCost;
    @OneToOne
    private Issue issue;
}
