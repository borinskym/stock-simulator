package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import org.springframework.stereotype.Service;

@Service
public class HarrySimpleSimulationRunner implements SimulationRunner {
    @Override
    public double run(SimulationRequest request) {
        return 8000;
    }
}
