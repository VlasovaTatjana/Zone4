package com.cs2trade.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRecordDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marketHashName;
    private String direction;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private Float floatValue;
    private LocalDate buyDate;
    private LocalDate sellDate;
    private Integer quantity;
    @ElementCollection
    private List<String> stickers;
    private LocalDateTime createdAt = LocalDateTime.now();

    public BigDecimal getProfit() {
        if (sellPrice == null || buyPrice == null) return BigDecimal.ZERO;
        return sellPrice.subtract(buyPrice);
    }

}