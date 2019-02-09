package com.borinskym.stock.simulator;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SimulationRequest {
    @NotEmpty
    private Integer initialAmount;
    private Map<String, Double> percentageBySymbol;
}
