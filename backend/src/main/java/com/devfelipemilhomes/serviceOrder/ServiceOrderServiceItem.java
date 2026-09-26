package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.serviceItem.ServiceItem;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "service_order_service_item")
@Data
public class ServiceOrderServiceItem {

    @EmbeddedId
    private ServiceOrderServiceItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_order_id")
    @MapsId("serviceOrderId")
    private ServiceOrder serviceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_item_id")
    @MapsId("serviceItemId")
    private ServiceItem serviceItem;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}
