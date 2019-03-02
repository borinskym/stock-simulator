package com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.date.SparseDate;
import com.borinskym.stock.simulator.runners.SimulationRunner;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/simulation")
public class SimulationController {

    @Autowired
    @Qualifier("stocksInfo")
    TreeMap<SparseDate, Map<String, Double>> stocksInfo;

    @Autowired
    @Qualifier("stocksSymbols")
    Set<String> stocksSymbols;

    @PostMapping("/run")
    public ResponseEntity<String> runSimulation(@RequestBody SimulationRequest simulationRequest) {
        validate(simulationRequest);
        return ResponseEntity.ok(new Gson().toJson(
                SimulationResponse.from(new SimulationRunner(simulationRequest, stocksInfo).run())));
    }

    private void validate(SimulationRequest simulationRequest) {
        validatePercentage(simulationRequest);
        validateSymbols(simulationRequest);
    }

    private void validateSymbols(SimulationRequest simulationRequest) {
        Set<String> notFoundSymbols = simulationRequest.getPercentageBySymbol().keySet()
                .stream()
                .filter(symbol -> !stocksSymbols.contains(symbol))
                .collect(Collectors.toSet());

        if(! notFoundSymbols.isEmpty()){
            throw new RestValidationException(String.format("symbols didnt found: %s", notFoundSymbols.stream().collect(Collectors.joining(","))));
        }
    }

    private void validatePercentage(SimulationRequest simulationRequest) {
        if (sumRequestPercentages(simulationRequest) != 1.0) {
            throw new RestValidationException("percentage doesnt sum to 1.0");
        }
    }

    private double sumRequestPercentages(SimulationRequest simulationRequest) {
        return simulationRequest.getPercentageBySymbol().values().stream()
                .mapToDouble(a -> a)
                .sum();
    }


    public static class RestValidationException extends RuntimeException{
        public RestValidationException(String message) {
            super(message);
        }
    }
}