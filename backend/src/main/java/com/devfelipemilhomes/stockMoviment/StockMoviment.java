package com.devfelipemilhomes.stockMoviment;

import com.devfelipemilhomes.serviceOrder.ServiceOrderPart;
import com.devfelipemilhomes.stock.Stock;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_moviment")
@Data
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


}
