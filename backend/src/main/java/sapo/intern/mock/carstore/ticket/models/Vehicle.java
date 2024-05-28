package sapo.intern.mock.carstore.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Table(name = "vehicles")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plateNumber;
    private String type;
    private String brand;
    private String color;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    public void setVehicle(Vehicle vehicle) {
        this.plateNumber = vehicle.plateNumber;
        this.brand = vehicle.brand;
        this.type = vehicle.type;
        this.color = vehicle.color;
    }
}
