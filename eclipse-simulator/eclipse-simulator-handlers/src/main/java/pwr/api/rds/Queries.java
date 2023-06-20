package pwr.api.rds;

public interface Queries
{
    String CREATE_DATABASE =
            "CREATE TABLE IF NOT EXISTS game ( " +
            "id SERIAL PRIMARY KEY, " +
            "name varchar(255), " +
            "date varchar(255), " +
            "winner varchar(255) " +
            ");" +

            "CREATE TABLE IF NOT EXISTS round ( " +
            "id SERIAL PRIMARY KEY, " +
            "game_id int, " +
            "number int " +
            ");" +

            "CREATE TABLE IF NOT EXISTS move ( " +
            "id SERIAL PRIMARY KEY, " +
            "round_id int, " +
            "round_number int, " +
            "player_id int, " +
            "number int, " +
            "action varchar(255) " +
            ");" +

            "CREATE TABLE IF NOT EXISTS fight ( " +
            "id SERIAL PRIMARY KEY, " +
            "game_id int, " +
            "round_id int, " +
            "attacking_player varchar(255), " +
            "defending_player varchar(255), " +
            "winner varchar(255)" +
            ");" +

            "CREATE TABLE IF NOT EXISTS fight_result ( " +
            "id SERIAL PRIMARY KEY, " +
            "fight_id int, " +
            "attacking_player_win_rate float, " +
            "defending_player_win_rate float" +
            ");" +

            "CREATE TABLE IF NOT EXISTS player ( " +
            "id SERIAL PRIMARY KEY, " +
            "name varchar(255)" +
            ");" +

            "CREATE TABLE IF NOT EXISTS game_player_relationship ( " +
            "id SERIAL PRIMARY KEY, " +
            "game_id int, " +
            "player_id int" +
            ");" +

            "CREATE TABLE IF NOT EXISTS round_fight_relationship ( " +
            "id SERIAL PRIMARY KEY, " +
            "round_id int, " +
            "fight_id int" +
            ");" +

            "CREATE TABLE IF NOT EXISTS result ( " +
            "id SERIAL PRIMARY KEY, " +
            "round_id int, " +
            "fight_id int" +
            ");";

    String CREATE_UPSERT_PLAYER_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_upsert_player(playerName character varying) \n" +
            "   RETURNS integer AS \n" +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "     v_id integer; \n" +
            "   BEGIN \n" +
            "     INSERT INTO player (name) \n" +
            "     VALUES(playerName); \n" +
            "     SELECT MAX(id) FROM player into v_id; \n" +
            "     RETURN v_id; \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_UPSERT_GAME_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_upsert_game(gameName character varying, date character varying, winner character varying) \n" +
            "   RETURNS integer AS \n " +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "     v_id integer; \n" +
            "   BEGIN \n" +
            "     INSERT INTO game (name, date, winner) \n" +
            "     VALUES(gameName, date, winner); \n" +
            "     SELECT MAX(id) FROM game into v_id; \n" +
            "     RETURN v_id; \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_UPSERT_ROUND_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_upsert_round(gameId integer, roundNumber integer) \n" +
            "   RETURNS integer AS \n" +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "     v_id integer; \n" +
            "   BEGIN \n" +
            "     INSERT INTO round (game_id, number) \n" +
            "     VALUES(gameId, roundNumber); \n" +
            "     SELECT MAX(id) FROM round into v_id; \n" +
            "     RETURN v_id; \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_UPSERT_FIGHT_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_upsert_fight(gameId integer, roundId integer," +
            "       attackingPlayer character varying, defendingPlayer character varying, " +
            "       winner character varying) \n" +
            "   RETURNS integer AS \n" +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "     v_id integer; \n" +
            "   BEGIN \n" +
            "     INSERT INTO fight (game_id, round_id, attacking_player, defending_player, winner) \n" +
            "     VALUES(gameId, roundId, attackingPlayer, defendingPlayer,  winner); \n" +
            "     SELECT MAX(id) FROM fight into v_id; \n" +
            "     RETURN v_id as id; \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_UPSERT_GAME_PLAYER_RELATIONSHIP_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_upsert_game_player_relationships(gameId integer, playerIds integer[]) \n" +
            " RETURNS void as \n" +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "       id int; \n" +
            "   BEGIN\n"  +
            "       FOREACH id IN ARRAY playerIds LOOP \n" +
            "               RAISE NOTICE 'here: %', id;" +
                    " \n" +
            "               INSERT INTO game_player_relationship (game_id, player_id) \n" +
            "               VALUES (gameId, id); \n" +
            "       END LOOP; \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_DELETE_GAME_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_delete_game(gameId integer) \n" +
            " RETURNS void as \n" +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "       row RECORD; \n" +
            "   BEGIN\n"  +
            "       DELETE FROM game WHERE id = gameId; \n" +
            "       FOR row IN SELECT * FROM round WHERE game_id = gameId LOOP \n" +
            "           DELETE FROM move WHERE round_id = row.id; \n" +
            "       END LOOP;" +
            "       DELETE FROM round WHERE game_id = gameId; \n" +
            "       FOR row IN SELECT * FROM fight WHERE game_id = gameId LOOP \n" +
            "           DELETE FROM fight_result WHERE fight_id = row.id; \n" +
            "           DELETE FROM round_fight_relationship WHERE fight_id = row.id; \n" +
            "       END LOOP;" +
            "       DELETE FROM fight WHERE game_id = gameId; \n" +
            "       DELETE FROM game_player_relationship WHERE game_id = gameId; \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_UPSERT_MOVE_FUNCTION =
            " CREATE OR REPLACE FUNCTION fn_upsert_move(roundId integer, playerId integer, moveNumber integer, action character varying) \n" +
            " RETURNS void as \n" +
            " $BODY$ \n" +
            "   DECLARE \n" +
            "   BEGIN\n"  +
            "       INSERT INTO move (round_id, player_id, number, action) \n" +
            "       VALUES (roundId, playerId, moveNumber, action); \n" +
            "   END; \n" +
            " $BODY$ \n" +
            " LANGUAGE plpgsql VOLATILE; \n";

    String CREATE_FUNCTIONS = CREATE_UPSERT_PLAYER_FUNCTION
            + CREATE_UPSERT_GAME_FUNCTION
            + CREATE_UPSERT_ROUND_FUNCTION
            + CREATE_UPSERT_FIGHT_FUNCTION
            + CREATE_UPSERT_GAME_PLAYER_RELATIONSHIP_FUNCTION
            + CREATE_DELETE_GAME_FUNCTION
            + CREATE_UPSERT_MOVE_FUNCTION;

    String DELETE_ALL_FUNCTIONS =
            "DROP FUNCTION IF EXISTS fn_upsert_player;" +
            "DROP FUNCTION IF EXISTS fn_upsert_game;" +
            "DROP FUNCTION IF EXISTS fn_upsert_round;" +
            "DROP FUNCTION IF EXISTS fn_upsert_fight;" +
            "DROP FUNCTION IF EXISTS fn_upsert_game_player_relationship;" +
            "DROP FUNCTION IF EXISTS fn_delete_game;" +
            "DROP FUNCTION IF EXISTS fn_upsert_move;";

    String DELETE_ALL_TABLES =
            "DROP TABLE IF EXISTS fight;" +
            "DROP TABLE IF EXISTS fight_result;" +
            "DROP TABLE IF EXISTS game;" +
            "DROP TABLE IF EXISTS game_player_relationship;" +
            "DROP TABLE IF EXISTS move;" +
            "DROP TABLE IF EXISTS player;" +
            "DROP TABLE IF EXISTS round_fight_relationship;" +
            "DROP TABLE IF EXISTS round;";

    String POPULATE_DATABASE_WITH_TEST_DATA =
            "INSERT INTO player (name) " +
            "VALUES('test player name 1');" +
            "INSERT INTO player (name) " +
            "VALUES('test player name 2');" +
            "INSERT INTO player (name) " +
            "VALUES('test player name 3');";

    String SELECT_PLAYER =
            "SELECT * FROM player WHERE name = ?";

    String SELECT_PLAYERS_FROM_GAME_BY_GAME_ID =
            "SELECT player.id, player.name FROM player " +
            "JOIN game_player_relationship on player.id = game_player_relationship.player_id " +
            "WHERE game_player_relationship.game_id = ?";

    String SELECT_ROUNDS_FROM_GAME_BY_GAME_ID =
            "SELECT * FROM round " +
            "WHERE game_id = ?";

    String UPSERT_PLAYER =
            "SELECT * FROM fn_upsert_player(?)";

    String DELETE_PLAYER =
            "DELETE FROM player WHERE ID = ?";

    String SELECT_GAME =
            "SELECT * FROM game WHERE name = ?";

    String UPSERT_GAME =
            "SELECT * FROM fn_upsert_game(?, ?, ?)";

    String DELETE_GAME =
            "SELECT * FROM fn_delete_game(?)";

    String UPSERT_GAME_PLAYER_RELATIONSHIPS =
            "SELECT * FROM fn_upsert_game_player_relationships(?, ?)";

    String UPSERT_ROUND =
            "SELECT * FROM fn_upsert_round(?, ?)";

    String UPSERT_MOVE =
            "SELECT * FROM fn_upsert_move(?, ?, ?, ?)";

    String SELECT_MOVES_BY_ROUND_ID =
            "SELECT * FROM move " +
            "WHERE round_id = ?";

    String SELECT_GAMES_BY_PLAYER_NAME =
            "select g.id, g.name, g.date, g.winner from game g \n" +
            "join player p on p.name = ? \n" +
            "join game_player_relationship gpr on gpr.player_id = p.id \n" +
            "where g.id = gpr.game_id";

    String SELECT_FIGHTS_BY_GAME_ID =
            "SELECT * FROM fight " +
            "WHERE game_id = ?";

    String SELECT_FIGHTS_BY_ROUND_ID =
            "SELECT * FROM fight " +
            "WHERE round_id = ?";

    String UPSERT_FIGHT =
            "INSERT INTO fight (game_id, round_id, attacking_player, defending_player, winner) " +
            "VALUES(?, ?, ?, ?, ?)";

    String UPSERT_FIGHT_RESULT =
            "INSERT INTO fight_result (fight_id, attacking_player_win_rate, defending_player_win_rate) " +
            "VALUES(?, ?, ?)";
}
