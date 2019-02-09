package com.borinskym.stock.simulator.runners;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class CalculateTotalAmountCommand {
    Map<String, Double> stocksHoldings;
    Map<String, Double> priceByStockName;

    public double execute() {
        return stocksHoldings.entrySet().stream()
                .mapToDouble(e -> e.getValue() * priceByStockName.get(e.getKey()))
                .sum();

    }
}
