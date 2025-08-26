package com.cs2trade.dto;

import com.cs2trade.dto.TradeRecordRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeRecordRequestTest {

    @Test
    void testRecordFieldsAccess() {
        TradeRecordRequest req = new TradeRecordRequest(
                "AWP | Dragon Lore",
                "Steam > External",
                new BigDecimal("10.50"),
                new BigDecimal("15.75"),
                0.0045f,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 1),
                1,
                List.of("Crown", "Titan")
        );

        assertEquals("AWP | Dragon Lore", req.marketHashName());
        assertEquals("Steam > External", req.direction());
        assertEquals(new BigDecimal("10.50"), req.buyPrice());
        assertEquals(new BigDecimal("15.75"), req.sellPrice());
        assertEquals(0.0045f, req.floatValue());
        assertEquals(LocalDate.of(2024, 5, 1), req.buyDate());
        assertEquals(LocalDate.of(2024, 6, 1), req.sellDate());
        assertEquals(1, req.quantity());
        assertEquals(List.of("Crown", "Titan"), req.stickers());
    }

}