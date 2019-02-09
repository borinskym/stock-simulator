package com.borinskym.stock.simulator.runners;

import lombok.AllArgsConstructor;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
public class CalculateStockHoldingCommand {
    Double totalAmount;
    Map<String, Double> priceByStockName;
    Map<String, Double> percentageBySymbol;
    public Map<String, Double> execute(){
        return percentageBySymbol.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, this::stockAmount));
    }

    private double stockAmount(Map.Entry<String, Double> e) {
        return (e.getValue()* totalAmount)/priceByStockName.get(e.getKey());
    }
}
