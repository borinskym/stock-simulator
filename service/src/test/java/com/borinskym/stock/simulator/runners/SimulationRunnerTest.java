package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import com.borinskym.stock.simulator.date.SparseDate;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimulationRunnerTest {

    @Test
    public void shouldCompute() {
        assertThat(new SimulationRunner(request(), stocksInfo()).run(), is(22458.333333333336));
    }

    private SimulationRequest request() {
        return SimulationRequest.builder()
                .initialAmount(10000.0)
                .percentageBySymbol(
                        ImmutableMap.of(
                                "a", 0.5,
                                "b", 0.5
                        ))
                .build();
    }

    private TreeMap<SparseDate, Map<String, Double>> stocksInfo() {
        return new TreeMap<>(
                ImmutableMap.of(
                        SparseDate.from(2014, 1), ImmutableMap.of(
                                "a", 20.0,
                                "b", 25.0),
                        SparseDate.from(2014, 2), ImmutableMap.of(
                                "a", 40.0,
                                "b", 60.0
                        ),
                        SparseDate.from(2014, 3), ImmutableMap.of(
                                "a", 35.0,
                                "b", 70.0
                        )));
    }

}