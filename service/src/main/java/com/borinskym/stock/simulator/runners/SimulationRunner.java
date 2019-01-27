package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import com.borinskym.stock.simulator.StockInfo;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SimulationRunner {
    private SimulationRequest request;
    private Map<String, List<StockInfo>> stocksInfo;

    public double run() {
        return 8000;
    }
}
