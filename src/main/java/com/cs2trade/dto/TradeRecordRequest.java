package com.cs2trade.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TradeRecordRequest(
        @NotBlank
        String marketHashName,
        @NotBlank
        String direction,
        @NotNull @DecimalMin("0.0")
        BigDecimal buyPrice,
        @DecimalMin("0.0")
        BigDecimal sellPrice,
        @DecimalMin("0.0")
        Float floatValue,
        LocalDate buyDate,
        LocalDate sellDate,
        @Min(1)
        Integer quantity,
        List<String> stickers
) {

}