package com.devfelipemilhomes.serviceOrder.serviceOrderServiceItem;

import com.devfelipemilhomes.serviceItem.ServiceItem;
import com.devfelipemilhomes.serviceOrder.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceOrderServiceItemRepository extends JpaRepository<ServiceOrderServiceItem, ServiceOrderServiceItemId> {
    Optional<ServiceOrderServiceItem> findByServiceOrderAndServiceItem(ServiceOrder serviceOrder, ServiceItem serviceItem);
    boolean existsByServiceOrderAndServiceItem(ServiceOrder serviceOrder, ServiceItem serviceItem);
}
