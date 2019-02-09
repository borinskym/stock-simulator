package com.borinskym.stock.simulator.runners;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculateStockHoldingCommandTest {

    @Test
    public void shouldCalculateStockHoldings() {
        Double totalAmount = 1000.0;
        Map<String, Double> priceByStockName = ImmutableMap.of("a", 250.0, "b", 500.0);
        Map<String, Double> percentageBySymbol = ImmutableMap.of("a", 0.5, "b", 0.5);

        assertThat(new CalculateStockHoldingCommand(totalAmount, priceByStockName, percentageBySymbol).execute(),
                is(ImmutableMap.of(
                        "a", 2.0,
                        "b", 1.0)));
    }

}