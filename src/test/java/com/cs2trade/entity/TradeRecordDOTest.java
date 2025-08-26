package com.cs2trade.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TradeRecordDOTest {

    @Test
    void testProfitCalculation() {
        TradeRecordDO trade = new TradeRecordDO();
        trade.setBuyPrice(new BigDecimal("12.50"));
        trade.setSellPrice(new BigDecimal("18.90"));

        BigDecimal profit = trade.getProfit();
        assertEquals(new BigDecimal("6.40"), profit);
    }

    @Test
    void testProfitNullValues() {
        TradeRecordDO trade = new TradeRecordDO();
        trade.setBuyPrice(null);
        trade.setSellPrice(new BigDecimal("15.00"));

        assertEquals(BigDecimal.ZERO, trade.getProfit());
    }

    @Test
    void testSettersAndGetters() {
        TradeRecordDO trade = new TradeRecordDO();
        trade.setMarketHashName("AWP | Dragon Lore");
        trade.setDirection("Steam > External");
        trade.setFloatValue(0.05f);
        trade.setQuantity(1);
        trade.setBuyDate(LocalDate.now());
        trade.setSellDate(LocalDate.now().plusDays(1));
        trade.setStickers(List.of("Titan", "Crown"));

        assertEquals("AWP | Dragon Lore", trade.getMarketHashName());
        assertEquals("Steam > External", trade.getDirection());
        assertEquals(0.05f, trade.getFloatValue());
        assertEquals(1, trade.getQuantity());
        assertEquals(2, trade.getStickers().size());
    }

    @Test
    void testGetProfit() {
        TradeRecordDO trade = new TradeRecordDO();
        trade.setBuyPrice(new BigDecimal("20.00"));
        trade.setSellPrice(new BigDecimal("30.00"));

        assertEquals(new BigDecimal("10.00"), trade.getProfit());

        trade.setSellPrice(null);
        assertEquals(BigDecimal.ZERO, trade.getProfit());
    }

    @Test
    void testEntityGettersSettersEqualsHashCodeToString() {
        TradeRecordDO trade1 = createTradeUsingConstructor();
        TradeRecordDO trade2 = createTradeUsingConstructor();

        assertEquals(trade1, trade2);
        assertEquals(trade1.hashCode(), trade2.hashCode());
        assertTrue(trade1.toString().contains("AK-47 | Redline"));
        assertEquals("BUY", trade1.getDirection());
        assertEquals(0.01f, trade1.getFloatValue());
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), trade1.getCreatedAt());
    }

    private TradeRecordDO createTradeUsingConstructor() {
        return new TradeRecordDO(
                1L,
                "AK-47 | Redline",
                "BUY",
                new BigDecimal("20.00"),
                new BigDecimal("30.00"),
                0.01f,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 2),
                1,
                List.of("Crown", "Katowice 2014"),
                LocalDateTime.of(2023, 1, 1, 12, 0)
        );
    }

}