package com.borinskym.stock.simulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SimulationRequest {
    @NotEmpty
    private Integer initialAmount;
    List<StockCalcInfo> stockCalcInfoList;
}
