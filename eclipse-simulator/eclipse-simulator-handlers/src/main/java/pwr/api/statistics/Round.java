package pwr.api.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Round
{
    private int id;
    private int gameId;
    private int roundNumber;
    private List<Move> moves;
    private List<Fight> fights;

    public Round(int gameId, int roundNumber, List<Move> moves, List<Fight> fights)
    {
        this.gameId = gameId;
        this.roundNumber = roundNumber;
        this.moves = moves;
        this.fights = fights;
    }
}
