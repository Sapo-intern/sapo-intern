package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "service_part")
@Entity
public class ServicePart {
    @Id
    @ManyToOne
    @JoinColumn(name = "part_id")
    private RepairPart repairPart;
    @Id
    @ManyToOne
    @JoinColumn(name = "service_id")
    private RepairService repairService;
    private int quantity;
}
