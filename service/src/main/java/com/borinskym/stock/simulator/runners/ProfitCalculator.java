package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import com.borinskym.stock.simulator.date.SparseDate;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.borinskym.stock.simulator.date.SparseDate.parse;

/*
        1. start with initialAmount dollars
        2. spilt the initialAmount dollars amongs the stocks: calculate how much stock from every kind
        3. for every time frame do :
            3.1 get the money back from the investment
            3.2 split the money again to quantity of the stocks

        4. get the money total based on the stocks and the last socksInfo
     */
@AllArgsConstructor
public class ProfitCalculator {
    private SimulationRequest request;
    private TreeMap<SparseDate, Map<String, Double>> stocksInfo;

    public double calculate() {

        return calculate(stocksInfo.subMap(
                parse(request.getStartDate()),
                true,
                parse(request.getEndDate()),
                true));
    }

    private double calculate(SortedMap<SparseDate, Map<String, Double>> input) {
        Map<String, Double> amountByStockName = calculateInitialSockHoldings(input);
        input.remove(input.firstKey());

        for (Map<String, Double> priceByStockName : input.values()) {
            double current = new CalculateTotalAmountCommand(amountByStockName, priceByStockName)
                    .execute();
            amountByStockName = calculateStockAmounts(priceByStockName, current);
        }
        return calculateFinalValue(amountByStockName, input);
    }

    private double calculateFinalValue(Map<String, Double> amountByStockName, SortedMap<SparseDate, Map<String, Double>> subMap) {
        return new CalculateTotalAmountCommand(amountByStockName, subMap.get(subMap.lastKey()))
                .execute();
    }

    private Map<String, Double> calculateInitialSockHoldings(SortedMap<SparseDate, Map<String, Double>> subMap) {

        return calculateStockAmounts(subMap.get(subMap.firstKey()), request.getInitialAmount());
    }

    private Map<String, Double> calculateStockAmounts(Map<String, Double> priceByStock, Double initialAmount) {
        return new CalculateStockHoldingCommand(
                initialAmount,
                priceByStock,
                request.getPercentageBySymbol())
                .execute();
    }
}