package sapo.intern.mock.carstore.ticket.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "customers")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂĐĨŨƠăđĩũơƯăđĩũơƯẠ-ỹ ]*$", message = "String must contain only Vietnamese alphabetic characters and spaces")
    @Size(max = 30, message = "String must have a maximum of 30 characters")
    private String name;
    @Pattern(regexp = "\\d{9,11}", message = "String must contain 9 to 11 digits")
    private String phoneNumber;
    @Email
    private String email;
    private String address;
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Ticket> tickets = new ArrayList<>();

    public void setCustomer(Customer customer) {
        this.email = customer.email;
        this.name = customer.name;
        this.phoneNumber = customer.phoneNumber;
    }

    public void addTicket(Ticket newTicket) {
        newTicket.setCustomer(this);
        tickets.add(newTicket);
    }
}
