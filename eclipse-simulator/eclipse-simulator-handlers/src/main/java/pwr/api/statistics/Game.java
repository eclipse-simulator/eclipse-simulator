package pwr.api.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Game
{
    private List<String> players;
    private List<Round> rounds;

    public void saveGame()
    {

    }
}
