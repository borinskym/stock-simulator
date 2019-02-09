package com.borinskym.stock.simulator;

import com.borinskym.stock.simulator.runners.SimulationRunner;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/v1/simulation")
public class SimulationController {

    @Autowired
    @Qualifier("stocksInfo")
    TreeMap<Long, Map<String, Double>> stocksInfo;

    @PostMapping("/run")
    public ResponseEntity<String> runSimulation(@RequestBody SimulationRequest simulationRequest){
            return ResponseEntity.ok(new Gson().toJson(
                    SimulationResponse.from(new SimulationRunner(simulationRequest, stocksInfo).run())));
    }
}