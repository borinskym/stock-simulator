package com.borinskym.stock.simulator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockInfo {
    Long timestamp;
    double value;
    public static StockInfo from(Long timestamp, double value){
        return StockInfo.builder()
                .timestamp(timestamp)
                .value(value)
                .build();
    }
}
