package sapo.intern.mock.carstore.ticket.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.intern.mock.carstore.global.response.ApiResponse;
import sapo.intern.mock.carstore.ticket.models.Vehicle;
import sapo.intern.mock.carstore.ticket.services.VehicleService;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleService vehicleService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Vehicle>>> requestGetVehicles(@RequestParam String plateNumber) {
        return ResponseEntity.ok(new ApiResponse<List<Vehicle>>("1000", vehicleService.getVehiclesByPlateNumber(plateNumber)));
    }

    @PostMapping("/{vehicleId}/")
    public ResponseEntity<ApiResponse<Vehicle>> requestUpdateVehicles( @PathVariable("vehicleId") Long vehicleId, @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(new ApiResponse<>("1020", vehicleService.updateVehicle(vehicleId, vehicle)));
    }

}
