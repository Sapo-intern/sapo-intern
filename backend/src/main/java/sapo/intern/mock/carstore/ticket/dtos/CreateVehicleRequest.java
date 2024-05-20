package sapo.intern.mock.carstore.ticket.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sapo.intern.mock.carstore.ticket.models.Vehicle;

@Getter
@Setter
@NoArgsConstructor
public class CreateVehicleRequest {
    private Vehicle vehicle;
}
