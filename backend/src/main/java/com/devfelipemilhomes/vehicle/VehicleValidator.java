package com.devfelipemilhomes.vehicle;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VehicleValidator {
    private final VehicleRepository reporitory;

    public VehicleValidator(VehicleRepository reporitory){
        this.reporitory = reporitory;
    }

    public void validate(Vehicle vehicle){
        if(reporitory.existsByPlate(vehicle.getPlate())){
            Vehicle vehicleRepo = reporitory.findByPlate(vehicle.getPlate());
            if(!Objects.equals(vehicle.getId(), vehicleRepo.getId())) {
                throw new DuplicateFieldException("Plate already registered");
            }
        }
    }
}
