package sapo.intern.mock.carstore.ticket.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sapo.intern.mock.carstore.global.exceptions.NotFoundException;
import sapo.intern.mock.carstore.ticket.models.Vehicle;
import sapo.intern.mock.carstore.ticket.repositories.VehicleRepo;

import java.util.List;

@AllArgsConstructor
@Service
public class VehicleService {
    private VehicleRepo vehicleRepo;


    public List<Vehicle> getVehiclesByPlateNumber(String plateNumber) {
        var vehicles = vehicleRepo.findAllByPlateNumber(plateNumber);
        if (vehicles.isEmpty()) {
            throw new NotFoundException("Không tim thấy phương tiện!");
        }
        return vehicles;
    }

    public Vehicle updateVehicle(Long vehicleId, Vehicle vehicle) {
        var foundVehicle = vehicleRepo.findById(vehicleId).orElseThrow(()-> new NotFoundException("Khong ton tai phuong tien!"));
        foundVehicle.setVehicle(vehicle);
        return vehicleRepo.save(foundVehicle);
    }
}
