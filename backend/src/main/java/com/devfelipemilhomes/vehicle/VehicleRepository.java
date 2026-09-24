package com.devfelipemilhomes.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    public boolean existsByPlate(String plate);
}
