package pwr.api.handler;

import pwr.api.rds.RDSClient;
import pwr.api.request.RDSRequestData;
import pwr.api.response.RDSResponseData;
import pwr.api.statistics.Game;
import pwr.api.statistics.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RDSHandler extends BaseHandler<RDSRequestData, RDSResponseData>
{
    @Override
    protected RDSResponseData processRequest(RDSRequestData request)
    {
        RDSClient client = new RDSClient();

        System.out.println("RDSHandler");

        switch(request.getOperationType())
        {
            case GET_GAMES:
                List<Game> games = new ArrayList<>();
                for(String gameName: request.getGameNames())
                {
                    games.add(client.getGame(gameName));
                }
                games = games.stream().filter(game -> game.getId() != -1).collect(Collectors.toList());
                return RDSResponseData.builder().games(games).build();

            case UPSERT_GAME:
                client.upsertGame(request.getGame());
                return RDSResponseData.builder().games(Collections.singletonList(client.getGame(request.getGame().getName()))).build();

            case GET_PLAYERS:
                List<Player> players = new ArrayList<>();
                for(String playerName: request.getPlayerNames())
                {
                    players.add(client.getPlayer(playerName));
                }
                players = players.stream().filter(player -> player.getId() != -1).collect(Collectors.toList());
                return RDSResponseData.builder().players(players).build();

            default:
                return null;
        }
    }

    @Override
    protected void validateRequest(RDSRequestData request)
    {

    }
}
