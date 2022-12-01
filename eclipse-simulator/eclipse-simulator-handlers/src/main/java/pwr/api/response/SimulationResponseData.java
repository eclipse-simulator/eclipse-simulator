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
    float attackingPlayerWinRate;
    float defendingPlayerWinRate;

    public SimulationResponseData(SimulationResult simulationResult)
    {
        this.attackingPlayerWinRate = simulationResult.getAttackingPlayerWinRate();
        this.defendingPlayerWinRate = simulationResult.getDefendingPlayerWinRate();
    }
}
