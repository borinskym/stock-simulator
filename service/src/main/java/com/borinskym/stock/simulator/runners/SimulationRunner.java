package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.TreeMap;
/*
        1. start with initialAmount dollars
        2. spilt the initialAmount dollars amongs the stocks: calculate how much stock from every kind
        3. for every time frame do :
            3.1 get the money back from the investment
            3.2 split the money again to quantity of the stocks

        4. get the money total based on the stocks and the last socksInfo
     */
@AllArgsConstructor
public class SimulationRunner {
    private SimulationRequest request;
    private TreeMap<Long, Map<String, Double>> stocksInfo;

    public double run() {
        Map<String, Double> amountByStockName = calculateStockAmounts(stocksInfo.firstEntry().getValue(), request.getInitialAmount());
        stocksInfo.remove(stocksInfo.firstEntry().getKey());

        for (Map<String, Double> priceByStockName : stocksInfo.values()) {
            double current = new CalculateTotalAmountCommand(amountByStockName, priceByStockName)
                    .execute();
            amountByStockName  = calculateStockAmounts(priceByStockName, current);
        }
        return new CalculateTotalAmountCommand(amountByStockName, stocksInfo.lastEntry().getValue())
                .execute();
    }

    private Map<String, Double> calculateStockAmounts(Map<String, Double> priceByStock, Double initialAmount) {
        return new CalculateStockHoldingCommand(
                initialAmount,
                priceByStock,
                request.getPercentageBySymbol())
                .execute();
    }


}