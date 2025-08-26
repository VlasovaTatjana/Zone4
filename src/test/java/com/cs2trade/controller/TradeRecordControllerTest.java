package com.cs2trade.controller;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import com.cs2trade.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TradeRecordControllerTest {

    private TradeService service;
    private TradeRecordController controller;

    @BeforeEach
    void setUp() {
        service = mock(TradeService.class);
        controller = new TradeRecordController(service);
    }

    @Test
    void testGetAllTrades() {
        TradeRecordDO trade = new TradeRecordDO();
        trade.setMarketHashName("AK-47 | Redline");

        when(service.getAll()).thenReturn(List.of(trade));

        ResponseEntity<List<TradeRecordDO>> response = controller.all();

        assertEquals(200, response.getStatusCode().value());
        List<TradeRecordDO> result = response.getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("AK-47 | Redline", result.getFirst().getMarketHashName());
        verify(service, times(1)).getAll();
    }

    @Test
    void testGetTradeById() {
        TradeRecordDO trade = new TradeRecordDO();
        trade.setId(42L);
        trade.setMarketHashName("AWP | Dragon Lore");

        when(service.getById(42L)).thenReturn(trade);

        ResponseEntity<TradeRecordDO> response = controller.getById(42L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("AWP | Dragon Lore", response.getBody().getMarketHashName());
        verify(service).getById(42L);
    }

    @Test
    void testAddTrade() {
        TradeRecordRequest request = createRequest();
        TradeRecordDO savedTrade = new TradeRecordDO();
        savedTrade.setMarketHashName("AK-47 | Redline");

        when(service.add(ArgumentMatchers.any())).thenReturn(savedTrade);

        ResponseEntity<TradeRecordDO> response = controller.add(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        TradeRecordDO result = response.getBody();
        assertNotNull(result);
        assertEquals("AK-47 | Redline", result.getMarketHashName());
        verify(service).add(request);
    }

    @Test
    void testUpdateTrade() {
        TradeRecordRequest request = createRequest();

        ResponseEntity<Void> response = controller.update(1L, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service).update(1L, request);
    }

    private TradeRecordRequest createRequest() {
        return new TradeRecordRequest(
                "AK-47 | Redline", "BUY",
                new BigDecimal("20.00"), new BigDecimal("30.00"),
                0.01f, LocalDate.now(), LocalDate.now().plusDays(1),
                1, List.of("Crown")
        );
    }

}