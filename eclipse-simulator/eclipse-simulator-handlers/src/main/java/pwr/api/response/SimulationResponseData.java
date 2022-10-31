package pwr.api.response;

import lombok.*;
import pwr.api.simulation.SimulationResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class SimulationResponseData
{
    float firstPlayerWinRate;
    float secondPlayerWinRate;

    public SimulationResponseData(SimulationResult simulationResult)
    {
        this.firstPlayerWinRate = simulationResult.getFirstPlayerWinRate();
        this.secondPlayerWinRate = simulationResult.getSecondPlayerWinRate();
    }
}
