package com.devfelipemilhomes.serviceItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    boolean existsByName(String name);
    ServiceItem findByName(String name);
}
