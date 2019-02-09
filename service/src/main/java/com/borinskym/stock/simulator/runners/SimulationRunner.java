package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@AllArgsConstructor
public class SimulationRunner {
    private SimulationRequest request;
    private TreeMap<Long, Map<String, Double>> stocksInfo;

    public double run() {
        double currentAmount = request.getInitialAmount();
        for (Long key : stocksInfo.keySet()) {
            double tempAmount = 0;
            for (Map.Entry<String, Double> percentageBySymbol : request.getPercentageBySymbol().entrySet()) {
                tempAmount += (currentAmount * percentageBySymbol.getValue()) * stocksInfo.get(key).get(percentageBySymbol.getKey());
            }
            currentAmount = tempAmount;
        }
        return currentAmount;
    }
}