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

        Map<String, Double> amountByStockName = calculateStockAmounts(firstValue(input), request.getInitialAmount());
        input.remove(input.firstKey());

        for (Map<String, Double> priceByStockName : input.values()) {
            double current = calcTotal(amountByStockName, priceByStockName);
            amountByStockName = calculateStockAmounts(priceByStockName, current);
        }

        return calcTotal(amountByStockName, lastStockInfo(input));
    }

    private Map<String, Double> firstValue(SortedMap<SparseDate, Map<String, Double>> input) {
        return input.get(input.firstKey());
    }

    private Map<String, Double> lastStockInfo(SortedMap<SparseDate, Map<String, Double>> input) {
        return input.get(input.lastKey());
    }

    private double calcTotal(Map<String, Double> amountByStock, Map<String, Double> priceByStockName){
        return amountByStock.entrySet().stream()
                .mapToDouble(e -> e.getValue() * priceByStockName.get(e.getKey()))
                .sum();
    }

    private Map<String, Double> calculateStockAmounts(Map<String, Double> priceByStock, Double initialAmount) {
        return new CalculateStockHoldingCommand(
                initialAmount,
                priceByStock,
                request.getPercentageBySymbol())
                .execute();
    }
}