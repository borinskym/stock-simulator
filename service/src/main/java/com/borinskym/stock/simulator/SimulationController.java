package com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.date.SparseDate;
import com.borinskym.stock.simulator.runners.ProfitCalculator;
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
        new InputValidator(
                stocksInfo,
                simulationRequest,
                stocksSymbols)
                .validate();

        return ResponseEntity.ok(new Gson().toJson(
                SimulationResponse.from(new ProfitCalculator(simulationRequest, new TreeMap<>(stocksInfo)).calculate())));
    }

}