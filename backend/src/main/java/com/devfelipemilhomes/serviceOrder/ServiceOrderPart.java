package com.devfelipemilhomes.serviceOrder;

import com.devfelipemilhomes.part.Part;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "service_order_part")
@Data
public class ServiceOrderPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_order_id")
    private ServiceOrder serviceOrder;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @Column(name = "quantity_reserved")
    private Integer quantityReserved;

    @Column(name = "quantity_used")
    private Integer quantityUsed;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}
