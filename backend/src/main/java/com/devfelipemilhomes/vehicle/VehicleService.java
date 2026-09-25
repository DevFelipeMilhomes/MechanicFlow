package com.devfelipemilhomes.vehicle;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.vehicle.dto.VehicleRequestDTO;
import com.devfelipemilhomes.vehicle.dto.VehicleResponseDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository repository;
    private final VehicleValidator validator;

    public VehicleService(VehicleRepository repository, VehicleValidator validator){
        this.repository = repository;
        this.validator = validator;
    }

    public VehicleResponseDTO create(VehicleRequestDTO dto){
        Vehicle vehicle = new Vehicle();
        String plate = dto.plate()
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();
        vehicle.setPlate(plate);
        vehicle.setBrand(dto.brand());
        vehicle.setModel(dto.model());
        vehicle.setProprietor(dto.proprietor());

        validator.validate(vehicle);

        repository.save(vehicle);

        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getProprietor()
        );

    }

    public VehicleResponseDTO findById(Long id){
        Vehicle vehicle = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Vehicle not found"));

        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getProprietor()
        );
    }

    public void delete(Long id){
        Vehicle vehicle = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vehicle not found"));
        repository.delete(vehicle);
    }

    public VehicleResponseDTO update(Long id, VehicleRequestDTO dto){
        Vehicle vehicle = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Vehicle not found"));
        String plate = dto.plate()
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();
        vehicle.setPlate(plate);
        vehicle.setBrand(dto.brand());
        vehicle.setModel(dto.model());
        vehicle.setProprietor(dto.proprietor());

        validator.validate(vehicle);

        repository.save(vehicle);

        return new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getProprietor()
        );
    }

    public List<VehicleResponseDTO> searchByExample(String plate, String brand, String model, String proprietor){
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(plate);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setProprietor(proprietor);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id")
                .withMatcher(
                        "brand",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "model",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "plate",
                        ExampleMatcher.GenericPropertyMatchers.exact()
                )
                .withMatcher(
                        "proprietor",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                );

        Example<Vehicle> vehicleExample = Example.of(vehicle,matcher);

        List<Vehicle> vehicleList = repository.findAll(vehicleExample);

        return vehicleList.stream().map(
                v -> new VehicleResponseDTO(
                        v.getId(),
                        v.getPlate(),
                        v.getBrand(),
                        v.getModel(),
                        v.getProprietor()
                )
        ).collect(Collectors.toList());
    }
}
