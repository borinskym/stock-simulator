package com.borinskym.stock.simulator.runners;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculateTotalAmountCommandTest {

    @Test
    public void shouldCalculateTotlaAmount() {
        Map<String, Double> stocksHoldings = ImmutableMap.of(
                "a", 20.0);
        Map<String, Double> priceByStockName = ImmutableMap.of(
                "a", 100.0);


        assertThat(new CalculateTotalAmountCommand(stocksHoldings, priceByStockName).execute(),
                is(20.0 * 100.0));
    }

}