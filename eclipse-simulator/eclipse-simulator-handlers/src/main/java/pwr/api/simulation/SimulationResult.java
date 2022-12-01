package pwr.api.simulation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimulationResult
{
    float attackingPlayerWinRate;
    float defendingPlayerWinRate;
}
