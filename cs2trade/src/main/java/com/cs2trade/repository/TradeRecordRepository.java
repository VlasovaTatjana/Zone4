package com.cs2trade.repository;

import com.cs2trade.entity.TradeRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRecordRepository extends JpaRepository<TradeRecordDO, Long> {

}