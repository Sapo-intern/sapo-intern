package sapo.intern.mock.carstore.ticket.models;

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
    private String name;
    private String brand;
    private String color;
    @OneToOne(mappedBy = "vehicle")
    private Ticket ticket;
}
