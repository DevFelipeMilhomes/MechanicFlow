package com.devfelipemilhomes.vehicle;

import com.devfelipemilhomes.serviceOrder.ServiceOrder;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<ServiceOrder> serviceOrders;

    private String plate;

    private String brand;

    private String model;

    private String proprietor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProprietor() {
        return proprietor;
    }

    public void setProprietor(String proprietor) {
        this.proprietor = proprietor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        return id != null && id.equals(vehicle.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
