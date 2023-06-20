package pwr.api.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game
{
    private int id;
    private String name;
    private String date;
    private List<Player> players;
    private List<Round> rounds;
    private String winner;
}
