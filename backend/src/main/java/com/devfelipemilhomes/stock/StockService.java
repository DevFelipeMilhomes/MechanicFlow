package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.part.PartRepository;
import com.devfelipemilhomes.stock.dto.StockRequestDTO;
import com.devfelipemilhomes.stock.dto.StockResponseDTO;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class StockService {
    private final StockRepository repository;
    private final StockValidator validator;
    private final StockMapper mapper;
    private final PartRepository partRepository;

    public StockService(StockRepository repository, StockValidator validator, StockMapper mapper, PartRepository partRepository){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.partRepository = partRepository;
    }

    public StockResponseDTO create(StockRequestDTO dto){
        Part part = partRepository.findById(dto.partId()).orElseThrow(()-> new ResourceNotFoundException("Part not found"));
        Stock stock = mapper.toEntity(dto);
        stock.setPart(part);

        validator.validate(stock);
        repository.save(stock);

        return mapper.toResponse(stock);
    }

    public StockResponseDTO findById(Long id){
        Stock stock = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Stock not found"));
        return mapper.toResponse(stock);
    }

    public void update(Long id, StockRequestDTO dto){
        Stock stock = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Stock not found"));
        Part part = partRepository.findById(dto.partId()).orElseThrow(()->new ResourceNotFoundException("part not found"));
        stock.setPart(part);
        stock.setQuantityOnHand(dto.quantityOnHand());
        stock.setQuantityReserved(dto.quantityReserved());
        stock.setUpdatedAt(OffsetDateTime.now());
        validator.validate(stock);
        repository.save(stock);
    }

    public void delete(Long id){
        Stock stock = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Stock not found"));
        repository.delete(stock);
    }

    public List<StockResponseDTO> findAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
