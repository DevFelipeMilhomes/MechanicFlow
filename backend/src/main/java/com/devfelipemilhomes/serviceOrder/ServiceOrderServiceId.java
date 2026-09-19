package com.devfelipemilhomes.serviceOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ServiceOrderServiceId implements Serializable {

    @Column(name = "service_order_id")
    private Long serviceOrderId;

    @Column(name = "service_id")
    private Long serviceId;
}
