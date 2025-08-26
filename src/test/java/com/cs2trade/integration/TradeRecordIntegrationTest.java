package com.cs2trade.integration;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import com.cs2trade.repository.TradeRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TradeRecordIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TradeRecordRepository repo;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/trades";
    }

    @Test
    void testGetById() {
        TradeRecordRequest request = new TradeRecordRequest(
                "M4A1-S | Hyper Beast", "BUY",
                new BigDecimal("50.00"), new BigDecimal("75.00"),
                0.04f, LocalDate.now(), LocalDate.now().plusDays(2),
                1, List.of("iBUYPOWER", "Titan")
        );

        ResponseEntity<TradeRecordDO> postResponse = restTemplate.postForEntity(
                getBaseUrl(), request, TradeRecordDO.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        TradeRecordDO created = postResponse.getBody();
        assertNotNull(created);
        Long id = created.getId();

        ResponseEntity<TradeRecordDO> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/" + id, TradeRecordDO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("M4A1-S | Hyper Beast", getResponse.getBody().getMarketHashName());
    }

    @Test
    void testGetById_NotFound() {
        Long nonexistentId = 999999L;

        ResponseEntity<String> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + nonexistentId, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("not found"));
    }

    @Test
    void testCreateAndFetchTrade() {
        TradeRecordRequest request = new TradeRecordRequest(
                "AWP | Asiimov", "BUY",
                new BigDecimal("100.00"), new BigDecimal("150.00"),
                0.05f, LocalDate.now(), LocalDate.now().plusDays(3),
                1, List.of("Crown", "Katowice")
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TradeRecordRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TradeRecordDO> postResponse = restTemplate.postForEntity(
                getBaseUrl(), entity, TradeRecordDO.class);

        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        TradeRecordDO created = postResponse.getBody();
        assertNotNull(created);
        assertEquals("AWP | Asiimov", created.getMarketHashName());

        ResponseEntity<TradeRecordDO[]> getResponse = restTemplate.getForEntity(
                getBaseUrl(), TradeRecordDO[].class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertTrue(getResponse.getBody().length > 0);
    }

    @Test
    void testCreateTrade_InvalidInput() {
        TradeRecordRequest invalid = new TradeRecordRequest(
                "", "BUY", new BigDecimal("-10.00"), new BigDecimal("30.00"),
                0.01f, LocalDate.now(), LocalDate.now().plusDays(1), 1, List.of("Sticker")
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TradeRecordRequest> entity = new HttpEntity<>(invalid, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl(), entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateTrade() {
        TradeRecordRequest create = new TradeRecordRequest(
                "AK-47 | Redline", "BUY",
                new BigDecimal("20.00"), new BigDecimal("30.00"),
                0.01f, LocalDate.now(), LocalDate.now().plusDays(1),
                1, List.of("Crown")
        );

        ResponseEntity<TradeRecordDO> postResponse = restTemplate.postForEntity(
                getBaseUrl(), create, TradeRecordDO.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        Long id = postResponse.getBody().getId();

        TradeRecordRequest update = new TradeRecordRequest(
                "AK-47 | Redline Updated", "SELL",
                new BigDecimal("25.00"), new BigDecimal("35.00"),
                0.02f, LocalDate.now(), LocalDate.now().plusDays(2),
                2, List.of("Titan")
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TradeRecordRequest> updateEntity = new HttpEntity<>(update, headers);
        restTemplate.exchange(getBaseUrl() + "/" + id, HttpMethod.PUT, updateEntity, Void.class);

        TradeRecordDO updated = restTemplate.getForObject(getBaseUrl() + "/" + id, TradeRecordDO.class);

        assertEquals("AK-47 | Redline Updated", updated.getMarketHashName());
        assertEquals("SELL", updated.getDirection());

        assertEquals("AK-47 | Redline Updated", updated.getMarketHashName());
        assertEquals("SELL", updated.getDirection());
    }

}