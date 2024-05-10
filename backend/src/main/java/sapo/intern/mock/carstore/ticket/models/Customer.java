package sapo.intern.mock.carstore.ticket.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "String must contain only alphanumeric characters")
    @Size(max = 30, message = "String must have a maximum of 30 characters")
    private String name;
    @Pattern(regexp = "\\d{9,11}", message = "String must contain 9 to 11 digits")
    private String phoneNumber;
    @Email
    private String email;
    @OneToOne(mappedBy = "customer")
    private Ticket ticket;
}
