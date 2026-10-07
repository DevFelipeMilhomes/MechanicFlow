package com.devfelipemilhomes.stock;

import com.devfelipemilhomes.exception.ResourceNotFoundException;
import com.devfelipemilhomes.part.Part;
import com.devfelipemilhomes.part.PartRepository;
import com.devfelipemilhomes.serviceOrder.serviceOrderPart.ServiceOrderPart;
import com.devfelipemilhomes.stock.dto.StockRequestDTO;
import com.devfelipemilhomes.stock.dto.StockResponseDTO;
import com.devfelipemilhomes.stock.stockMoviment.*;
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
    private final StockMovimentRepository stockMovimentRepository;
    private final StockMovimentMapper stockMovimentMapper;

    public StockService(
            StockRepository repository,
            StockValidator validator,
            StockMapper mapper,
            PartRepository partRepository,
            StockMovimentRepository stockMovimentRepository,
            StockMovimentMapper stockMovimentMapper
    ){
        this.repository = repository;
        this.validator = validator;
        this.mapper = mapper;
        this.partRepository = partRepository;
        this.stockMovimentRepository = stockMovimentRepository;
        this.stockMovimentMapper = stockMovimentMapper;
    }

    public StockResponseDTO create(StockRequestDTO dto){
        Part part = partRepository.findById(dto.partId()).orElseThrow(()-> new ResourceNotFoundException("Part not found"));
        Stock stock = mapper.toEntity(dto);
        stock.setPart(part);

        validator.validate(stock);
        repository.save(stock);

        StockMoviment stockMoviment1 = stockMovimentMapper.toEntry(stock,dto.quantityOnHand());
        if(dto.quantityReserved()>0){
            StockMoviment stockMoviment2 = stockMovimentMapper.toReservation(stock,dto.quantityReserved(), null);
            stockMovimentRepository.save(stockMoviment2);
        }
        stockMovimentRepository.save(stockMoviment1);

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
        StockMoviment stockMoviment1 = stockMovimentMapper.toAdjustment(stock,dto.quantityOnHand());
        if(dto.quantityReserved()>0){
            StockMoviment stockMoviment2 = stockMovimentMapper.toReservation(stock,dto.quantityReserved(), null);
            stockMovimentRepository.save(stockMoviment2);
        }
        stockMovimentRepository.save(stockMoviment1);
        repository.save(stock);
    }

    public void delete(Long id){
        Stock stock = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Stock not found"));
        repository.delete(stock);
    }

    @Transactional
    public void reserve(Integer quantityReserve, Long partId, ServiceOrderPart serviceOrderPart){
        Part part = partRepository.findById(partId).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        Stock stock = repository.findByPart(part);
        validator.validateReservation(quantityAvailable(stock), quantityReserve);
        stock.setQuantityReserved(stock.getQuantityReserved() + quantityReserve);
        repository.save(stock);
        StockMoviment stockMoviment = stockMovimentMapper.toReservation(stock, quantityReserve, serviceOrderPart);
        stockMovimentRepository.save(stockMoviment);
    }

    @Transactional
    public void consume(Integer quantityConsume, Long partId, ServiceOrderPart serviceOrderPart){
        Part part = partRepository.findById(partId).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        Stock stock = repository.findByPart(part);
        stock.setQuantityOnHand(stock.getQuantityOnHand()-quantityConsume);
        stock.setQuantityReserved(stock.getQuantityReserved()-quantityConsume);
        repository.save(stock);
        StockMoviment stockMoviment = stockMovimentMapper.toConsumption(stock, quantityConsume, serviceOrderPart);
        stockMovimentRepository.save(stockMoviment);
    }

    @Transactional
    public void release(Integer quantityRelease, Long partId, ServiceOrderPart serviceOrderPart){
        Part part = partRepository.findById(partId).orElseThrow(()->new ResourceNotFoundException("Part not found"));
        Stock stock = repository.findByPart(part);
        stock.setQuantityReserved(stock.getQuantityReserved()- quantityRelease);
        repository.save(stock);
        StockMoviment stockMoviment = stockMovimentMapper.toRelease(stock, quantityRelease, serviceOrderPart);
        stockMovimentRepository.save(stockMoviment);
    }

    public List<StockResponseDTO> findAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public List<StockMovimentResponseDTO> findAllStockMoviment(){
        return stockMovimentRepository.findAll().stream().map(stockMovimentMapper::toResponse).toList();
    }

    public static Integer quantityAvailable(Stock stock){
        return stock.getQuantityOnHand() - stock.getQuantityReserved();
    }
}
