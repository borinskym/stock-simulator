package com.borinskym.stock.simulator.config;

import com.borinskym.stock.simulator.runners.SimulationRunner;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;

@Configuration
public class SimulatorRunnersConfig {

    private SimulationRunner simulationRunner;

    @Autowired
    public SimulatorRunnersConfig(SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
    }

    @Bean(value = "simulationsRunnersByName")
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public Map<String, SimulationRunner> simulationsByName() {
        return ImmutableMap.of("harry-light", simulationRunner);
    }



}
