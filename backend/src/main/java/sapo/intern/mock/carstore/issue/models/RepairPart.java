package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name="vehicle_parts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RepairPart {
    @Id
    @Column(name="vehicle_part_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    /*Mnh giá theo .000 vnđ */
    private double unitPrice;
    /*Mnh giá theo .000 vnđ */
    private double totalPrice;

    @OneToMany(mappedBy = "repairPart")
    List<ServicePart> serviceParts;

}
