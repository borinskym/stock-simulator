package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimulationRunnerTest {

    @Test
    public void shouldCompute() {
        TreeMap<Long, Map<String, Double>> stocksInfo = new TreeMap<>(
                ImmutableMap.of(
                        1L, ImmutableMap.of(
                                "a", 20.0,
                                "b", 25.0),
                        2L, ImmutableMap.of(
                                "a", 40.0,
                                "b", 60.0
                        ),
                        3L, ImmutableMap.of(
                                "a", 35.0,
                                "b", 70.0
                        )));

        SimulationRequest request = SimulationRequest.builder()
                .initialAmount(10000)
                .percentageBySymbol(
                        ImmutableMap.of(
                                "a", 0.5,
                                "b", 0.5
                        ))
                .build();

        /*
            1. take quantity of each stock and multiply by price to get the full amount
            2. divide again the amount to asing each quantity of stock

         */
        assertThat(new SimulationRunner(request, stocksInfo).run(), is(22458.333333333336));


    }

}