package sapo.intern.mock.carstore.user.models;

import jakarta.persistence.*;
import lombok.*;
import sapo.intern.mock.carstore.user.enums.UserRole;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
@Entity
public class InvalidatedToken {
    @Id
    String id;
    Date expiryTime;
}
