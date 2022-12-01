package pwr.api.constants;

public interface Constants
{
    int MAX_ROLL = 6;
    int MAX_ROUNDS = 8;

    interface ships
    {
        String INTERCEPTOR = "INTERCEPTOR";
        String CRUISER = "CRUISER";
        String DREADNOUGHT = "DREADNOUGHT";
        int INTERCEPTOR_FIELDS_LIMIT = 4;
        int CRUISER_FIELDS_LIMIT = 6;
        int DREADNOUGHT_FIELDS_LIMIT = 8;
    }

    interface fields
    {
        String ION_CANNON = "ION_CANNON";
        String HULL = "HULL";
        String NUCLEAR_DRIVE = "NUCLEAR_DRIVE";
        String GLUON_COMPUTER = "GLUON_COMPUTER";
        String ENERGY_SOURCE_1 = "ENERGY_SOURCE_1";
        String ENERGY_SOURCE_2 = "ENERGY_SOURCE_2";
        String ENERGY_SOURCE_3 = "ENERGY_SOURCE_3";
    }

    interface aws
    {
        String COGNITO_CLIENT_ID = "29qq7vjdnoq2g47ipk9hetpoc0";
        String COGNITO_USER_POOL_ID = "eu-west-2_a686LLCkD";
    }

    interface dynamo
    {
        String DYNAMO_TABLE_NAME = "Simulations";
        String ATTACKING_FLEET_HASH = "attacking_fleet_hash";
        String DEFENDING_FLEET_HASH = "defending_fleet_hash";
        String ATTACKING_FLEET_WIN_RATE = "attacking_fleet_win_rate";
        String DEFENDING_FLEET_WIN_RATE = "defending_fleet_win_rate";
        String COUNTER = "counter";
    }
}
