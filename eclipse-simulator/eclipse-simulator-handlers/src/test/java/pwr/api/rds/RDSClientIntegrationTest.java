package pwr.api.rds;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pwr.api.enums.Action;
import pwr.api.BaseIntegrationTest;
import pwr.api.simulation.SimulationResult;
import pwr.api.statistics.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class RDSClientIntegrationTest extends BaseIntegrationTest
{
    private static RDSClient client;
    private static final String TEST_GAME_NAME = "test game name 1";
    private static final String TEST_PLAYER_NAME_1 = "test player name 1";
    private static final String TEST_PLAYER_NAME_2 = "test player name 2";
    private static final String TEST_PLAYER_NAME_3 = "test player name 3";

    @BeforeAll
    public static void init()
    {
        client = new RDSClient();
    }

    @Test
    @Order(1)
    public void shouldDeleteAllTables()
    {
        client.dropDatabase();
    }

    @Test
    @Order(2)
    public void shouldCreateDatabase()
    {
        client.createDatabase();
    }

    @Test
    @Order(3)
    public void shouldUpsertAndGetPlayer()
    {
        int randomInt = ThreadLocalRandom.current().nextInt(0, 10000 + 1);
        String name = String.valueOf(randomInt);
        Player newPlayer = new Player();
        newPlayer.setName(name);
        client.upsertPlayer(newPlayer);
        Player savedPlayer = client.getPlayer(name);
        assertEquals(name, savedPlayer.getName());
        client.deletePlayer(savedPlayer.getId());
    }

    @Test
    @Order(4)
    public void shouldUpsertGame()
    {   client.deleteGame(client.getGame(TEST_GAME_NAME).getId());
        upsertGame(TEST_GAME_NAME);
        Game savedGame = client.getGame(TEST_GAME_NAME);
        System.out.println(savedGame);
        client.deleteGame(client.getGame(TEST_GAME_NAME).getId());
    }

    @Test
    @Order(5)
    public void shouldReturnEmptyGame()
    {
        Game game = client.getGame("no game");
        assertEquals(-1, game.getId());
        assertNull(game.getName());
        assertNull(game.getDate());
        assertTrue(game.getPlayers().isEmpty());
        assertTrue(game.getRounds().isEmpty());
    }

    @Test
    @Order(6)
    public void shouldInsertTwoGames()
    {
        String gameName1 = "shouldInsertTwoGames game 1";
        String gameName2 = "shouldInsertTwoGames game 2";
        upsertGame(gameName1);
        upsertGame(gameName2);
        Game game1 = client.getGame(gameName1);
        Game game2 = client.getGame(gameName2);
        assertEquals(gameName1, game1.getName());
        assertEquals(gameName2, game2.getName());
        client.deleteGame(game1.getId());
        client.deleteGame(game2.getId());
    }

    @Test
    @Order(7)
    public void shouldGetAllPlayersGames()
    {
        String gameName = "shouldGetAllPlayersGames game ";
        upsertGame(gameName);
        assertFalse(client.getPlayersGames("test player name 1").isEmpty());
        client.deleteGame(client.getGame(gameName).getId());
    }

    private void upsertGame(String gameName)
    {
        Game game = new Game();
        game.setName(gameName);

        int gameId = (client.upsertGame(game));

        game.setPlayers(Arrays.asList(client.getPlayer(TEST_PLAYER_NAME_1), client.getPlayer(TEST_PLAYER_NAME_2), client.getPlayer(TEST_PLAYER_NAME_3)));

        ArrayList<Move> round1Moves = new ArrayList<>();
        round1Moves.add(new Move(1, 1, Action.MOVE));
        round1Moves.add(new Move(2, 2, Action.MOVE));
        round1Moves.add(new Move(3, 3, Action.MOVE));

        ArrayList<Move> round2Moves = new ArrayList<>();
        round2Moves.add(new Move(1, 1, Action.MOVE));
        round2Moves.add(new Move(2, 2, Action.MOVE));
        round2Moves.add(new Move(3, 3, Action.MOVE));

        ArrayList<Move> round3Moves = new ArrayList<>();
        round3Moves.add(new Move(1, 1, Action.MOVE));
        round3Moves.add(new Move(2, 2, Action.MOVE));
        round3Moves.add(new Move(3, 3, Action.MOVE));

        SimulationResult result = new SimulationResult(30, 70);
        Fight fight = new Fight(gameId, 0, TEST_PLAYER_NAME_1, TEST_PLAYER_NAME_2, TEST_PLAYER_NAME_2, result);

        game.setRounds(Arrays.asList(new Round(gameId, 1, round1Moves, List.of(fight)),
                new Round(gameId,  2, round2Moves, List.of(fight)),
                new Round(gameId,  3, round3Moves, List.of(fight))));
        client.upsertGame(game);
    }
}
