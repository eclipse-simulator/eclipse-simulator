package pwr.api.rds;

import pwr.api.enums.Action;
import pwr.api.exception.ESApiException;
import pwr.api.statistics.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static pwr.api.constants.Constants.errors.*;
import static pwr.api.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;
import static pwr.api.rds.Queries.*;

public class RDSClient
{
    private static final String ORG_POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://eclipsesimulatordb.cps1ndjdsaaa.eu-west-2.rds.amazonaws.com:5432/EclipseSimulator";
    private static final String USER = "postgres";
    private static final String PASSWORD = "6lrM4l0xMtz1PTn4mAdp";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String WINNER = "winner";
    private static final String GAME_ID = "game_id";
    private static final String ROUND_ID = "round_id";
    private static final String ATTACKING_PLAYER = "attacking_player";
    private static final String DEFENDING_PLAYER = "defending_player";

    public Game getGame(String name)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_GAME);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            int gameId = -1;
            String gameName = null;
            String date = null;
            String winner = null;
            while(resultSet.next())
            {
                gameId = resultSet.getInt(ID);
                gameName = resultSet.getString(NAME);
                date = resultSet.getString(DATE);
                winner = resultSet.getString(WINNER);
            }
            return new Game(gameId, gameName, date, getPlayersFromGame(gameId), getRoundsFromGame(gameId), winner);
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public Player getPlayer(String name)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_PLAYER);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            int playerId = -1;
            String playerName = null;
            while(resultSet.next())
            {
                playerId = resultSet.getInt(ID);
                playerName = resultSet.getString(NAME);
            }
            return new Player(playerId, playerName);
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public void deletePlayer(int playerId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_PLAYER);
            preparedStatement.setInt(1, playerId);
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    public void deleteGame(int gameId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_GAME);
            preparedStatement.setInt(1, gameId);
            preparedStatement.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    public List<Player> getPlayersFromGame(int gameId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_PLAYERS_FROM_GAME_BY_GAME_ID);
            preparedStatement.setInt(1, gameId);
            resultSet = preparedStatement.executeQuery();
            int playerId;
            String playerName;
            List<Player> players = new ArrayList<>();
            while(resultSet.next())
            {
                playerId = resultSet.getInt(ID);
                playerName = resultSet.getString(NAME);
                players.add(new Player(playerId, playerName));
            }
            return players;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public List<Round> getRoundsFromGame(int gameId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_ROUNDS_FROM_GAME_BY_GAME_ID);
            preparedStatement.setInt(1, gameId);
            resultSet = preparedStatement.executeQuery();
            int roundId;
            int roundNumber;
            List<Round> rounds = new ArrayList<>();
            while(resultSet.next())
            {
                roundId = resultSet.getInt(ID);
                roundNumber = resultSet.getInt("number");
                rounds.add(new Round(roundId, gameId, roundNumber, getMoves(roundId), getFightsByRoundId(roundId)));
            }
            return rounds;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public int upsertPlayer(Player player)
    {
        if(getPlayer(player.getName()).getId() != -1)
        {
            throw new ESApiException(ERROR, PLAYER_ALREADY_EXISTS_MESSAGE, INTERNAL_SERVER_ERROR);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_PLAYER);
            preparedStatement.setString(1, player.getName());
            resultSet = preparedStatement.executeQuery();
            int id = -1;
            while(resultSet.next())
            {
                id = resultSet.getInt("fn_upsert_player");
            }
            return id;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public List<Fight> getFightsByGameId(int gameId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_FIGHTS_BY_GAME_ID);
            preparedStatement.setInt(1, gameId);
            resultSet = preparedStatement.executeQuery();
            List<Fight> fights = new ArrayList<>();
            int id;
            int roundId;
            String attackingPlayer;
            String defendingPlayer;
            while(resultSet.next())
            {
                id = resultSet.getInt(ID);
                roundId = resultSet.getInt(ROUND_ID);
                attackingPlayer = resultSet.getString(ATTACKING_PLAYER);
                defendingPlayer = resultSet.getString(DEFENDING_PLAYER);
                fights.add(new Fight(id, gameId, roundId, attackingPlayer, defendingPlayer, null, null));
            }
            return fights;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public List<Fight> getFightsByRoundId(int roundId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_FIGHTS_BY_ROUND_ID);
            preparedStatement.setInt(1, roundId);
            resultSet = preparedStatement.executeQuery();
            List<Fight> fights = new ArrayList<>();
            int id;
            int fightGameId;
            String attackingPlayer;
            String defendingPlayer;
            while(resultSet.next())
            {
                id = resultSet.getInt(ID);
                fightGameId = resultSet.getInt(GAME_ID);
                attackingPlayer = resultSet.getString(ATTACKING_PLAYER);
                defendingPlayer = resultSet.getString(DEFENDING_PLAYER);
                fights.add(new Fight(id, fightGameId, roundId, attackingPlayer, defendingPlayer, null, null));
            }
            return fights;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public void upsertFights(Round round)
    {
        System.out.println("upsertFights 1");
        if(round.getFights() == null || round.getFights().isEmpty())
        {
            return;
        }
        for(Fight fight: round.getFights())
        {
            fight.setRoundId(round.getId());
            upsertFight(fight, round.getGameId(), round.getId());
        }
        System.out.println("upsertFights works");
    }

    public void upsertFight(Fight fight, int gameId, int roundId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_FIGHT);
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, roundId);
            preparedStatement.setString(3, fight.getAttackingPlayer());
            preparedStatement.setString(4, fight.getDefendingPlayer());
            preparedStatement.setString(5, fight.getWinner());
            System.out.println(preparedStatement);
            System.out.println("upsertFight 1");
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
        //upsertFightResult(fight);
    }

    public void upsertFightResult(Fight fight)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_FIGHT_RESULT);
            preparedStatement.setInt(1, fight.getId());
            preparedStatement.setFloat(2, fight.getResult().getAttackingPlayerWinRate());
            preparedStatement.setFloat(3, fight.getResult().getDefendingPlayerWinRate());
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    public int upsertGame(Game game)
    {
        System.out.println("upsertGame 1");
        int gameId = insertFreshGame(game);
        System.out.println("upsertGame 2");
        game.setId(gameId);
        upsertGamePlayerRelationships(game);
        System.out.println("upsertGame 3");
        upsertRounds(game);
        System.out.println("upsertGame 4");
        return gameId;
    }

    public void upsertRounds(Game game)
    {
        if(game.getRounds() == null || game.getRounds().isEmpty())
        {
            return;
        }
        for(Round round: game.getRounds())
        {
            round.setGameId(game.getId());
            upsertRound(round);
        }
        System.out.println("upsertRounds works");
    }

    public void upsertRound(Round round)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_ROUND);
            preparedStatement.setInt(1, round.getGameId());
            preparedStatement.setInt(2, round.getRoundNumber());
            resultSet = preparedStatement.executeQuery();
            int id;
            while (resultSet.next())
            {
                id = resultSet.getInt("fn_upsert_round");
                round.setId(id);
            }
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
        upsertMoves(round);
        upsertFights(round);
        System.out.println("upsertRound works");
    }

    public void createDatabase()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(CREATE_DATABASE);
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
        createFunctions();
        populateDatabaseWithTestData();
    }

    public void dropDatabase()
    {
        deleteAllTables();
        deleteAllFunctions();
    }

    public List<Game> getPlayersGames(String playerName)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_GAMES_BY_PLAYER_NAME);
            preparedStatement.setString(1, playerName);
            resultSet = preparedStatement.executeQuery();
            List<Game> games = new ArrayList<>();
            int id;
            String name;
            String date;
            String winner;
            while(resultSet.next())
            {
                id = resultSet.getInt(ID);
                name = resultSet.getString(NAME);
                date = resultSet.getString(DATE);
                winner = resultSet.getString(WINNER);
                games.add(new Game(id, name, date, getPlayersFromGame(id), getRoundsFromGame(id), winner));
            }
            return games;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    public List<Fight> getPlayersFights(String playerName)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_GAMES_BY_PLAYER_NAME);
            preparedStatement.setString(1, playerName);
            resultSet = preparedStatement.executeQuery();
            List<Fight> fights = new ArrayList<>();
            int id;
            int gameId;
            int roundId;
            String attackingPlayer;
            String defendingPlayer;
            String winner;
            while(resultSet.next())
            {
                id = resultSet.getInt(ID);
                gameId = resultSet.getInt(GAME_ID);
                roundId = resultSet.getInt(ROUND_ID);
                attackingPlayer = resultSet.getString(ATTACKING_PLAYER);
                defendingPlayer = resultSet.getString(DEFENDING_PLAYER);
                winner = resultSet.getString(WINNER);
                fights.add(new Fight(id, gameId, roundId, attackingPlayer, defendingPlayer, winner, null));
            }
            return fights;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    private void deleteAllTables()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_ALL_TABLES);
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private void deleteAllFunctions()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(DELETE_ALL_FUNCTIONS);
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private void createFunctions()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(CREATE_FUNCTIONS);
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private void populateDatabaseWithTestData()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(POPULATE_DATABASE_WITH_TEST_DATA);
            preparedStatement.executeUpdate();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private int insertFreshGame(Game game)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_GAME);
            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, getDate());
            preparedStatement.setString(3, game.getWinner());
            ResultSet resultSet = preparedStatement.executeQuery();
            int id = -1;
            while(resultSet.next())
            {
                id = resultSet.getInt("fn_upsert_game");
            }
            return id;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private List<Move> getMoves(int roundId)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT_MOVES_BY_ROUND_ID);
            preparedStatement.setInt(1, roundId);
            resultSet = preparedStatement.executeQuery();
            List<Move> moves = new ArrayList<>();
            int id;
            int playerId;
            int moveNumber;
            Action action;
            while(resultSet.next())
            {
                id = resultSet.getInt(ID);
                playerId = resultSet.getInt("player_id");
                moveNumber = resultSet.getInt("number");
                action = Action.valueOf(resultSet.getString("action"));
                moves.add(new Move(id, roundId, playerId, moveNumber, action));
            }
            return moves;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(resultSet, preparedStatement, connection);
        }
    }

    private void upsertMove(Move move)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_MOVE);
            preparedStatement.setInt(1, move.getRoundId());
            preparedStatement.setInt(2, move.getPlayerId());
            preparedStatement.setInt(3, move.getMoveNumber());
            preparedStatement.setString(4, move.getAction().toString());
            preparedStatement.execute();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private void upsertMoves(Round round)
    {
        if(round.getMoves() == null || round.getMoves().isEmpty())
        {
            return;
        }
        for(Move move: round.getMoves())
        {
            move.setRoundId(round.getId());
            upsertMove(move);
        }
        System.out.println("upsertMoves works");
    }

    private void upsertGamePlayerRelationships(Game game)
    {
        List<Player> players = game.getPlayers();
        int gameId = game.getId();
        if(players == null || players.isEmpty())
        {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(ORG_POSTGRESQL_DRIVER);
            connection = DriverManager
                    .getConnection(DB_URL,
                            USER, PASSWORD);
            preparedStatement = connection.prepareStatement(UPSERT_GAME_PLAYER_RELATIONSHIPS);
            Array playerIdsSQLArray = connection.createArrayOf("integer", players
                    .stream()
                    .map(Player::getId).toArray());
            preparedStatement.setInt(1, gameId);
            preparedStatement.setArray(2, playerIdsSQLArray);
            preparedStatement.executeQuery();
            System.out.println("upsertGamePlayerRelationships works");
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            throw new ESApiException(ERROR, UNEXPECTED_DATABASE_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        } finally
        {
            finishCall(preparedStatement, connection);
        }
    }

    private void finishCall(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection)
    {
        if(resultSet != null)
        {
            try
            {
                resultSet.close();
            } catch (SQLException e) {
                throw new ESApiException(ERROR, UNEXPECTED_ERROR_WHEN_CLOSING_RESULT_SET_MESSAGE, INTERNAL_SERVER_ERROR);
            }
        }
        if(preparedStatement != null)
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new ESApiException(ERROR, UNEXPECTED_ERROR_WHEN_CLOSING_STATEMENT_MESSAGE, INTERNAL_SERVER_ERROR);
            }
        }
        try
        {
            if(connection != null && !connection.isClosed())
            {
                connection.close();
            }
        } catch (Exception e)
        {
            throw new ESApiException(ERROR, UNEXPECTED_ERROR_WHEN_CLOSING_CONNECTION_MESSAGE, INTERNAL_SERVER_ERROR);
        }
    }

    private void finishCall(PreparedStatement preparedStatement, Connection connection)
    {
        if(preparedStatement != null)
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new ESApiException(ERROR, UNEXPECTED_ERROR_WHEN_CLOSING_STATEMENT_MESSAGE, INTERNAL_SERVER_ERROR);
            }
        }
        try
        {
            if(connection != null && !connection.isClosed())
            {
                connection.close();
            }
        } catch (Exception e)
        {
            throw new ESApiException(ERROR, UNEXPECTED_ERROR_WHEN_CLOSING_CONNECTION_MESSAGE, INTERNAL_SERVER_ERROR);
        }
    }

    private String getDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return dateFormat.format(timestamp);
    }
}
