package com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.runners.SimulationRunner;
import com.borinskym.stock.simulator.runners.SimulatorFactory;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/simulation")
public class SimulationController {
    @Autowired
    @Qualifier("simulationsRunnersByName")
    Map<String, SimulationRunner> simulationsByName;

    @PostMapping("/run")
    public ResponseEntity<String> runSimulation(@RequestBody SimulationRequest simulationRequest){
        try{
            SimulationRunner simulation = new SimulatorFactory(simulationsByName).getByName(simulationRequest.getStrategy());
            return ResponseEntity.ok(
                    new Gson().toJson(SimulationResponse.from(simulation.run(simulationRequest))));
        }catch (SimulatorFactory.CouldNotFindSimulation e){
            return ResponseEntity.badRequest().body("simulation name invalid");
        }
    }
}

