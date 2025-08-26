package com.cs2trade.service;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import com.cs2trade.exception.NotFoundException;
import com.cs2trade.mapper.TradeRecordMapper;
import com.cs2trade.repository.TradeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRecordRepository repo;
    private final TradeRecordMapper mapper;

    public List<TradeRecordDO> getAll() {
        return repo.findAll();
    }

    public TradeRecordDO getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Trade with id " + id + " not found"));
    }

    public TradeRecordDO add(TradeRecordRequest req) {
        TradeRecordDO trade = mapper.toEntity(req);
        return repo.save(trade);
    }

    public TradeRecordDO update(Long id, TradeRecordRequest req) {
        TradeRecordDO trade = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Trade with id " + id + " not found"));
        mapper.updateEntity(trade, req);
        return repo.save(trade);
    }

}