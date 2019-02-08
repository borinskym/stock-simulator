package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SimulationRunner {
    private SimulationRequest request;
    Map<Long, Map<String, Double>> stocksInfo;

    public double run() {
        return 8000;
    }
}
