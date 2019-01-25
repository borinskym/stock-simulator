package com.borinskym.stock.simulator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/simulation")
public class SimulationController {

    @PostMapping("/run")
    public ResponseEntity<SimulationResponse> runSimulation(@RequestBody SimulationRequest simulationRequest){
        return ResponseEntity.ok(SimulationResponse.builder().endAmount(8000).build());
    }

}

