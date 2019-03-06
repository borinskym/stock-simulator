package com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.SimulationRequest;
import com.borinskym.stock.simulator.date.SparseDate;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProfitCalculatorTest {


    private final double initialAmount = 10000.0;

    @Test
    public void shouldCompute() {
        assertThat(new ProfitCalculator(request("01/2014", "03/2014"), stocksInfo()).calculate(), is(22458.333333333336));
        assertThat(new ProfitCalculator(request("01/2014", "02/2014"), stocksInfo()).calculate(), is(22000.0));
    }

    private SimulationRequest request(String startDate, String endDate) {
        return SimulationRequest.builder()
                .initialAmount(initialAmount)
                .percentageBySymbol(
                        ImmutableMap.of(
                                "a", 0.5,
                                "b", 0.5
                        ))
                .startDate(startDate)
                .endDate(endDate)
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