package sapo.intern.mock.carstore.ticket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sapo.intern.mock.carstore.ticket.models.Vehicle;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVehicleRequest {
    private Vehicle vehicle;
}
