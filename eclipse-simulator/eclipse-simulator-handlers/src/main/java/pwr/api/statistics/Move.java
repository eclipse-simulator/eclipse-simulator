package pwr.api.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pwr.api.enums.Action;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move
{
    private int id;
    private int roundId;
    private int playerId;
    private int moveNumber;
    private Action action;

    public Move(int playerId, int moveNumber, Action action)
    {
        this.playerId = playerId;
        this.moveNumber = moveNumber;
        this.action = action;
    }
}
