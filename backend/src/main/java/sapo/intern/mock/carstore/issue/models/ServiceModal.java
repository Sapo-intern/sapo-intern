package sapo.intern.mock.carstore.issue.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String servicesCode;
    String name;
    Double price;
    String description;
}
