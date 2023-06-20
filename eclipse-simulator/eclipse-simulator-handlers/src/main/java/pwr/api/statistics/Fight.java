package pwr.api.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwr.api.simulation.SimulationResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fight
{
    private int id;
    private int gameId;
    private int roundId;
    private String attackingPlayer;
    private String defendingPlayer;
    private String winner;
    private SimulationResult result;

    public Fight(int gameId, int roundId, String attackingPlayer, String defendingPlayer, String winner, SimulationResult result)
    {
        this.gameId = gameId;
        this.roundId = roundId;
        this.attackingPlayer = attackingPlayer;
        this.defendingPlayer = defendingPlayer;
        this.winner = winner;
        this.result = result;
    }
}
