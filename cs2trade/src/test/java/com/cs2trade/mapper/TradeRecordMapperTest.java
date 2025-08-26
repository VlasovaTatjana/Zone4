package com.cs2trade.mapper;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import com.cs2trade.mapper.TradeRecordMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeRecordMapperTest {

    private final TradeRecordMapper mapper = new TradeRecordMapper();

    @Test
    void testToEntity() {
        TradeRecordRequest req = getSampleRequest();
        TradeRecordDO entity = mapper.toEntity(req);

        assertEquals(req.marketHashName(), entity.getMarketHashName());
        assertEquals(req.direction(), entity.getDirection());
        assertEquals(req.buyPrice(), entity.getBuyPrice());
        assertEquals(req.sellPrice(), entity.getSellPrice());
        assertEquals(req.floatValue(), entity.getFloatValue());
        assertEquals(req.buyDate(), entity.getBuyDate());
        assertEquals(req.sellDate(), entity.getSellDate());
        assertEquals(req.quantity(), entity.getQuantity());
        assertEquals(req.stickers(), entity.getStickers());
    }

    @Test
    void testUpdateEntity() {
        TradeRecordDO entity = new TradeRecordDO();
        TradeRecordRequest req = getSampleRequest();

        mapper.updateEntity(entity, req);

        assertEquals(req.marketHashName(), entity.getMarketHashName());
        assertEquals(req.direction(), entity.getDirection());
        assertEquals(req.buyPrice(), entity.getBuyPrice());
    }

    private TradeRecordRequest getSampleRequest() {
        return new TradeRecordRequest(
                "AK-47 | Redline",
                "Steam > External",
                new BigDecimal("20.00"),
                new BigDecimal("35.00"),
                0.007f,
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 3),
                1,
                List.of("Crown", "Katowice 2014")
        );
    }

}