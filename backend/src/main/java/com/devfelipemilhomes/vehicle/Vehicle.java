package com.devfelipemilhomes.vehicle;

import com.devfelipemilhomes.serviceOrder.ServiceOrder;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "vehicle")
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "service_order")
    private List<ServiceOrder> serviceOrders;

    private String plate;

    private String brand;

    private String model;

    private String proprietor;
}
