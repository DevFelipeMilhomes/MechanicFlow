package com.devfelipemilhomes.serviceOrder.serviceOrderPart;

import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.serviceOrder.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderPartRepository extends JpaRepository<ServiceOrderPart, Long> {
    Boolean existsByServiceOrderAndPart(ServiceOrder serviceOrder, Part part);
}
