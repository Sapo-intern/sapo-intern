package sapo.intern.mock.carstore.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sapo.intern.mock.carstore.ticket.models.Vehicle;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

}
