package pwr.api.response;

import lombok.*;
import pwr.api.statistics.Fight;
import pwr.api.statistics.Game;
import pwr.api.statistics.Player;
import pwr.api.statistics.Round;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RDSResponseData
{
    private List<Player> players;
    private List<Game> games;
    private List<Round> rounds;
    private List<Fight> fights;
}
