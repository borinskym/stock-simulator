package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;

public interface SimulationRunner {
    public abstract double run(SimulationRequest request);
}
