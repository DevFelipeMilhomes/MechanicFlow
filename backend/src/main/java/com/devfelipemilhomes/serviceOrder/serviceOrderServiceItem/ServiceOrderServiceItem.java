package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem;

import com.devfelipemilhomes.serviceItem.ServiceItem;
import com.devfelipemilhomes.serviceOrder.ServiceOrder;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "service_order_service_item")
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

    public ServiceOrderServiceItemId getId() {
        return id;
    }

    public void setId(ServiceOrderServiceItemId id) {
        this.id = id;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceOrderServiceItem serviceOrderServiceItem = (ServiceOrderServiceItem) o;

        return id != null && id.equals(serviceOrderServiceItem.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
