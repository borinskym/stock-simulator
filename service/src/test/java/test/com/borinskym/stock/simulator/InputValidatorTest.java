package test.com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.InputValidator;
import com.borinskym.stock.simulator.SimulationRequest;
import com.borinskym.stock.simulator.date.SparseDate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class InputValidatorTest {

    @Test(expected = InputValidator.ValidationFailed.class)
    public void shouldValidatePercentages() {
        new InputValidator(stocksInfo(),
                request(ImmutableMap.of("a", 0.25), "01/2014", "02/2014"),
                ImmutableSet.of("a")).validate();
    }

    private SimulationRequest request(ImmutableMap<String, Double> percentageBySymbol, String startDate, String endDate) {
        return SimulationRequest.builder()
                .percentageBySymbol(percentageBySymbol)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }


    @Test(expected = InputValidator.ValidationFailed.class)
    public void shouldValidateSymbols() {
        new InputValidator(stocksInfo(),
                request(ImmutableMap.of("a", 1.0), "01/2014", "02/2014"),
                ImmutableSet.of("b")
                ).validate();
    }

    @Test(expected = InputValidator.ValidationFailed.class)
    public void shouldValidateStartDate() {
        new InputValidator(stocksInfo(),
                request(ImmutableMap.of("a", 1.0), "01/2013", "02/2014"),
                ImmutableSet.of("a")).validate();
    }

    @Test(expected = InputValidator.ValidationFailed.class)
    public void shouldValidateEndDate() {
        new InputValidator(stocksInfo(),
                request(ImmutableMap.of("a", 1.0), "01/2014", "05/2014"),
                ImmutableSet.of("a")).validate();
    }

    private TreeMap<SparseDate, Map<String, Double>> stocksInfo() {
        return new TreeMap<>(ImmutableMap.of(
                SparseDate.from(2014, 1), ImmutableMap.of("a", 0.2),
                SparseDate.from(2014, 2), ImmutableMap.of("a", 0.3)
        ));
    }

    @Test(expected = InputValidator.ValidationFailed.class)
    public void shouldValidateDates_whenNotDateOnSpecific() {
        new InputValidator(new TreeMap<>(ImmutableMap.of(
                SparseDate.from(2014, 1), ImmutableMap.of("a", 0.2, "b", 0.3),
                SparseDate.from(2014, 2), ImmutableMap.of("a", 0.3)
        )),
                request(ImmutableMap.of("b", 1.0), "01/2014", "02/2014"),
                ImmutableSet.of("b")).validate();
    }

    @Test
    public void shouldPassValidation() {
        new InputValidator(stocksInfo(),
                request(ImmutableMap.of("a", 1.0), "01/2014", "02/2014"),
                ImmutableSet.of("a")).validate();
    }

}