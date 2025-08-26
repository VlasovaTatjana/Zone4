package com.cs2trade.service;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import com.cs2trade.exception.NotFoundException;
import com.cs2trade.mapper.TradeRecordMapper;
import com.cs2trade.repository.TradeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TradeServiceTest {

    private TradeRecordRepository repo;
    private TradeRecordMapper mapper;
    private TradeService service;

    @BeforeEach
    void setup() {
        repo = mock(TradeRecordRepository.class);
        mapper = mock(TradeRecordMapper.class);
        service = new TradeService(repo, mapper);
    }

    @Test
    void testAddTrade() {
        TradeRecordRequest request = createRequest();

        TradeRecordDO expectedEntity = new TradeRecordDO();
        expectedEntity.setMarketHashName(request.marketHashName());
        expectedEntity.setDirection(request.direction());
        expectedEntity.setBuyPrice(request.buyPrice());

        when(mapper.toEntity(any())).thenReturn(expectedEntity);
        when(repo.save(any())).thenReturn(expectedEntity);

        TradeRecordDO result = service.add(request);

        assertNotNull(result);
        assertEquals(request.marketHashName(), result.getMarketHashName());
        assertEquals(request.direction(), result.getDirection());
        assertEquals(request.buyPrice(), result.getBuyPrice());
    }

    @Test
    void testUpdateTrade() {
        TradeRecordRequest request = createRequest();
        TradeRecordDO existingTrade = new TradeRecordDO();
        existingTrade.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(existingTrade));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        doAnswer(invocation -> {
            TradeRecordDO entity = invocation.getArgument(0);
            TradeRecordRequest dto = invocation.getArgument(1);
            entity.setMarketHashName(dto.marketHashName());
            entity.setDirection(dto.direction());
            entity.setBuyPrice(dto.buyPrice());
            return null;
        }).when(mapper).updateEntity(any(), any());

        TradeRecordDO result = service.update(1L, request);

        assertEquals(1L, result.getId());
        assertEquals("AK-47 | Redline", result.getMarketHashName());
        assertEquals("Steam > External", result.getDirection());
        assertEquals(new BigDecimal("20.00"), result.getBuyPrice().setScale(2));

        verify(mapper).updateEntity(existingTrade, request);
    }

    @Test
    void testUpdateTrade_NotFound() {
        TradeRecordRequest request = createRequest();
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.update(99L, request));
    }

    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(List.of(new TradeRecordDO()));
        List<TradeRecordDO> all = service.getAll();
        assertEquals(1, all.size());
    }

    private TradeRecordRequest createRequest() {
        return new TradeRecordRequest(
                "AK-47 | Redline",
                "Steam > External",
                new BigDecimal("20.0"),
                new BigDecimal("30.0"),
                0.02f,
                LocalDate.now(),
                LocalDate.now(),
                1,
                Collections.singletonList("Sticker 1")
        );
    }

}