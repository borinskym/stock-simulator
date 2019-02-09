package com.borinskym.stock.simulator;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class StockCalcInfo {
    double percentage;
    String symbol;
}
