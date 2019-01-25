package com.borinskym.stock.simulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SimulationRequest {
    @NotEmpty
    private String strategy;
    @NotEmpty
    private Integer initialAmount;
}
