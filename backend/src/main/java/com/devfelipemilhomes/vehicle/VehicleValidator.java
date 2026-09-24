package com.devfelipemilhomes.vehicle;

import com.devfelipemilhomes.exception.DuplicateFieldException;
import org.springframework.stereotype.Component;

@Component
public class VehicleValidator {
    private final VehicleRepository reporitory;

    public VehicleValidator(VehicleRepository reporitory){
        this.reporitory = reporitory;
    }

    public void validate(Vehicle vehicle){
        if(reporitory.existsByPlate(vehicle.getPlate())){
            throw new DuplicateFieldException("Plate already registered");
        }
    }
}
