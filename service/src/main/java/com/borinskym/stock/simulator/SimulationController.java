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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/simulation")
public class SimulationController {

    @Autowired
    @Qualifier("stocksInfo")
    Map<String, List<StockInfo>> stocksInfo;

    @PostMapping("/run")
    public ResponseEntity<String> runSimulation(@RequestBody SimulationRequest simulationRequest){
            return ResponseEntity.ok(new Gson().toJson(
                    SimulationResponse.from(new SimulationRunner(simulationRequest, stocksInfo).run())));
    }
}

