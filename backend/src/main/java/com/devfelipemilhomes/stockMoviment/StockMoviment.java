package com.devfelipemilhomes.stockMoviment;

import com.devfelipemilhomes.serviceOrder.serviceOrderPart.ServiceOrderPart;
import com.devfelipemilhomes.stock.Stock;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_moviment")
public class StockMoviment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "service_order_part_id")
    private ServiceOrderPart serviceOrderPart;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type")
    private MovimentType movimentType;

    private Integer quantity;

    private String description;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public ServiceOrderPart getServiceOrderPart() {
        return serviceOrderPart;
    }

    public void setServiceOrderPart(ServiceOrderPart serviceOrderPart) {
        this.serviceOrderPart = serviceOrderPart;
    }

    public MovimentType getMovimentType() {
        return movimentType;
    }

    public void setMovimentType(MovimentType movimentType) {
        this.movimentType = movimentType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockMoviment stockMoviment = (StockMoviment) o;

        return id != null && id.equals(stockMoviment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
