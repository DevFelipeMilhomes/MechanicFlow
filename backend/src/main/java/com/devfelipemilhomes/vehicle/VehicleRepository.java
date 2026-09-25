package com.devfelipemilhomes.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByPlate(String plate);
    Vehicle findByPlate(String plate);
}
