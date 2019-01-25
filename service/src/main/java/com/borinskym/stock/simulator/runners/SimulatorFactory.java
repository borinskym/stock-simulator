package com.borinskym.stock.simulator.runners;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SimulatorFactory{

    private Map<String, SimulationRunner> simulationByName;

    public SimulatorFactory(Map<String, SimulationRunner> simulationByName) {
        this.simulationByName = simulationByName;
    }

    public SimulationRunner getByName(String name) {
        return Optional.ofNullable(simulationByName.get(name))
                .orElseThrow(() ->
                        new CouldNotFindSimulation(String.format("simulation not found %s", name)));
    }

    public static class CouldNotFindSimulation extends RuntimeException{
        public CouldNotFindSimulation(String message) {
            super(message);
        }
    }
}
