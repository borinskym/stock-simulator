package test.com.borinskym.stock.simulator.runners;

import com.borinskym.stock.simulator.runners.SimulationRunner;
import com.borinskym.stock.simulator.runners.SimulatorFactory;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SimulatorFactoryTest {

    private final String SIMULATION_NAME = "sim-a";
    private SimulationRunner dummySimulationRunner = request -> 0;
    private SimulatorFactory simulatorFactory = new SimulatorFactory(
            ImmutableMap.of(SIMULATION_NAME, dummySimulationRunner)
    );
    @Test
    public void shouldGetSimulationRunner() {
        assertTrue(simulatorFactory.getByName(SIMULATION_NAME) == dummySimulationRunner);
    }

    @Test(expected = SimulatorFactory.CouldNotFindSimulation.class)
    public void shouldFail_whenSimulationNotExist() {
        simulatorFactory.getByName("NOT_EXIST");
    }

}