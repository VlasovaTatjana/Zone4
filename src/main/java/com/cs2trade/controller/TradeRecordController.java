package com.cs2trade.controller;

import com.cs2trade.dto.TradeRecordRequest;
import com.cs2trade.entity.TradeRecordDO;
import com.cs2trade.service.TradeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trades")
@CrossOrigin(origins = "*")
public class TradeRecordController {

    private final TradeService service;

    @GetMapping
    public ResponseEntity<List<TradeRecordDO>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeRecordDO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TradeRecordDO> add(@RequestBody @Valid TradeRecordRequest req) {
        TradeRecordDO created = service.add(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid TradeRecordRequest req) {
        service.update(id, req);
        return ResponseEntity.noContent().build();
    }

}