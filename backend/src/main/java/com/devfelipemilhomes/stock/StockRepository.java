package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.part.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    boolean existsByPart(Part part);
    Stock findByPart(Part part);
}
