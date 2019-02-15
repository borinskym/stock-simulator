package com.borinskym.stock.simulator.runners;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class CalculateTotalAmountCommand {
    Map<String, Double> amountByStock;
    Map<String, Double> priceByStockName;

    public double execute() {
        return amountByStock.entrySet().stream()
                .mapToDouble(e -> e.getValue() * priceByStockName.get(e.getKey()))
                .sum();

    }
}
