package com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.date.SparseDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockInfo {
    SparseDate time;
    double value;
    public static StockInfo from(SparseDate date, double value){
        return StockInfo.builder()
                .time(date)
                .value(value)
                .build();
    }
}
