package sapo.intern.mock.carstore.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


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
//    @Column(unique = true)
    private String plateNumber;
    private String type;
    private String brand;
    private String color;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<Ticket> tickets = new ArrayList<>();

    public void setVehicle(Vehicle vehicle) {
        this.plateNumber = vehicle.plateNumber;
        this.brand = vehicle.brand;
        this.type = vehicle.type;
        this.color = vehicle.color;
    }

    public void addTicket(Ticket newTicket) {
        newTicket.setVehicle(this);
        tickets.add(newTicket);
    }
}
