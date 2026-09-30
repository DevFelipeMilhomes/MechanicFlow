package com.devfelipemilhomes.serviceItem;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.serviceItem.dto.ServiceItemRequestDTO;
import com.devfelipemilhomes.serviceItem.dto.ServiceItemResponseDTO;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceItemService {
    private final ServiceItemRepository repository;
    private final ServiceItemValidator validator;
    private final ServiceItemMapper mapper;

    public ServiceItemService(ServiceItemRepository repository, ServiceItemValidator validator, ServiceItemMapper mapper){
        this.repository = repository;
        this.validator = validator;;
        this.mapper = mapper;
    }

    public ServiceItemResponseDTO create(ServiceItemRequestDTO dto){
        ServiceItem serviceItem = mapper.toEntity(dto);

        validator.validate(serviceItem);
        repository.save(serviceItem);

        return mapper.toResponse(serviceItem);
    }

    public ServiceItemResponseDTO findById(Long id){
        ServiceItem serviceItem = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        return mapper.toResponse(serviceItem);
    }

    public void update(Long id, ServiceItemRequestDTO dto){
        ServiceItem serviceItem = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        mapper.toUpdate(dto, serviceItem);

        validator.validate(serviceItem);
        repository.save(serviceItem);
    }

    public void delete(Long id){
        ServiceItem serviceItem = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service not found"));
        repository.delete(serviceItem);
    }

    public List<ServiceItemResponseDTO> searchByExample(String name, String description, BigDecimal basePrice){
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setName(name);
        serviceItem.setDescription(description);
        serviceItem.setBasePrice(basePrice);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("id","createdAt")
                .withMatcher(
                        "name",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "description",
                        ExampleMatcher.GenericPropertyMatchers.contains()
                )
                .withMatcher(
                        "basePrice",
                        ExampleMatcher.GenericPropertyMatchers.exact()
                );

        Example<ServiceItem> serviceItemExample = Example.of(serviceItem, matcher);

        List<ServiceItem> serviceItemList = repository.findAll(serviceItemExample);

        return serviceItemList.stream().map(mapper::toResponse).toList();
    }
}
