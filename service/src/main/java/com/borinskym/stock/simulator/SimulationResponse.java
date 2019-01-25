package com.borinskym.stock.simulator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulationResponse {
    double endAmount;

    public static SimulationResponse from(double endAmount){
        return new SimulationResponse(endAmount);
    }
}
