package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.service.Service;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "service_order_service")
@Data
public class ServiceOrderService {

    @EmbeddedId
    private ServiceOrderServiceId id;

    @ManyToOne
    @JoinColumn(name = "service_order_id")
    @MapsId("serviceOrderId")
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @MapsId("serviceId")
    private Service service;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}
