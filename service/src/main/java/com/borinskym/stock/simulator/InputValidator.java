package com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.date.SparseDate;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.lang.String.format;

@AllArgsConstructor
public class InputValidator {
    private TreeMap<SparseDate, Map<String, Double>> stocksInfo;
    private SimulationRequest simulationRequest;
    Set<String> stocksSymbols;

    public void validate(){
        validatePercentage();
        validateSymbols();
        validateDates();
    }

    private void validateDates() {
        Set<String> stocks = simulationRequest.getPercentageBySymbol().keySet();

        SparseDate startDate = SparseDate.parse(simulationRequest.getStartDate());
        SparseDate endDate = SparseDate.parse(simulationRequest.getEndDate());

        validateDateExist(startDate);
        validateDateExist(endDate);

        Set<String> invalidSymbols = stocks.stream()
                .filter(symbol -> stocksInfo.get(startDate).get(symbol) == null ||
                        stocksInfo.get(endDate).get(symbol) == null)
                .collect(Collectors.toSet());

        if(!invalidSymbols.isEmpty()){
            throw new ValidationFailed(format("the following symbols are dont match dates: %s", invalidSymbols));
        }
    }

    private void validateDateExist(SparseDate startDate) {
        if(!stocksInfo.containsKey(startDate)){
            throw new ValidationFailed(format("dont have information on date %s", startDate));
        }
    }

    private void validateSymbols() {
        Set<String> notFoundSymbols = simulationRequest.getPercentageBySymbol().keySet()
                .stream()
                .filter(symbol -> !stocksSymbols.contains(symbol))
                .collect(Collectors.toSet());

        if (!notFoundSymbols.isEmpty()) {
            throw new ValidationFailed(String.format("symbols not found %s", notFoundSymbols));
        }
    }

    private void validatePercentage() {
        if (sumRequestPercentages(simulationRequest) != 1.0) {
            throw new ValidationFailed("percentage doesnt sum to 1.0");
        }
    }

    private double sumRequestPercentages(SimulationRequest simulationRequest) {
        return simulationRequest.getPercentageBySymbol().values().stream()
                .mapToDouble(a -> a)
                .sum();
    }






    public static class ValidationFailed extends RuntimeException{
        public ValidationFailed(String message) {
            super(message);
        }
    }
}
