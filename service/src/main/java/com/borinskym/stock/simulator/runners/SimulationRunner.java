package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;

public interface SimulationRunner {
    double run(SimulationRequest request);
}
