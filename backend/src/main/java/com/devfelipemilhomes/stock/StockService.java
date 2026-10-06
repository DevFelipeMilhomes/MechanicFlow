package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.part.PartRepository;
import com.devfelipemilhomes.stock.dto.StockRequestDTO;
import com.devfelipemilhomes.stock.dto.StockResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        mapper.toUpdate(dto, stock);
        stock.setPart(part);
        stock.setUpdatedAt(OffsetDateTime.now());
        validator.validate(stock);
        repository.save(stock);
    }

    public void delete(Long id){
        Stock stock = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Stock not found"));
        repository.delete(stock);
    }

    @Transactional
    public void reserve(Integer quantityReserve, Long partId){
        Part part = partRepository.findById(partId).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        Stock stock = repository.findByPart(part);
        validator.validateReservation(quantityAvailable(stock), quantityReserve);
        stock.setQuantityReserved(stock.getQuantityReserved() + quantityReserve);
        repository.save(stock);
    }

    @Transactional
    public void consume(Integer quantityConsume, Long partId){
        Part part = partRepository.findById(partId).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        Stock stock = repository.findByPart(part);
        stock.setQuantityOnHand(stock.getQuantityOnHand()-quantityConsume);
        stock.setQuantityReserved(stock.getQuantityReserved()-quantityConsume);
        repository.save(stock);
    }

    @Transactional
    public void release(Integer quantityRelease, Long partId){
        Part part = partRepository.findById(partId).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        Stock stock = repository.findByPart(part);
        stock.setQuantityReserved(stock.getQuantityReserved()- quantityRelease);
        repository.save(stock);
    }

    public List<StockResponseDTO> findAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public static Integer quantityAvailable(Stock stock){
        return stock.getQuantityOnHand() - stock.getQuantityReserved();
    }
}
