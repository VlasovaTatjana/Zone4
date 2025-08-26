package com.cs2trade.mapper;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import org.springframework.stereotype.Component;

@Component
public class TradeRecordMapper {

    public TradeRecordDO toEntity(TradeRecordRequest dto) {
        TradeRecordDO t = new TradeRecordDO();
        updateEntity(t, dto);
        return t;
    }

    public void updateEntity(TradeRecordDO entity, TradeRecordRequest dto) {
        entity.setMarketHashName(dto.marketHashName());
        entity.setDirection(dto.direction());
        entity.setBuyPrice(dto.buyPrice());
        entity.setSellPrice(dto.sellPrice());
        entity.setFloatValue(dto.floatValue());
        entity.setBuyDate(dto.buyDate());
        entity.setSellDate(dto.sellDate());
        entity.setQuantity(dto.quantity());
        entity.setStickers(dto.stickers());
    }

}