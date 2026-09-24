package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.part.Part;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "stock")
@Data
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private Part part;

    @Column(name = "quantity_on_hand")
    private Integer quantityOnHand;

    @Column(name = "quantity_reserved")
    private Integer quantityReserved;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

}
