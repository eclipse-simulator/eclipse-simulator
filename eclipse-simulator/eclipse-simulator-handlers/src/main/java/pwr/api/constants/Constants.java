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
        // TODO use env variables for this
        String COGNITO_CLIENT_ID = System.getenv("COGNITO_USER_POOL_CLIENT_ID");
        String COGNITO_USER_POOL_ID = System.getenv("COGNITO_USER_POOL_ID");
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

    interface errors
    {
        String WRONG_USERNAME_OR_PASSWORD_ERROR_MESSAGE = "Incorrect username or password.";
        String USER_DOES_NOT_EXIST_ERROR_MESSAGE = "User does not exist.";
        String AUTHENTICATION_ERROR_MESSAGE = "Wrong username or password";
        String INTERNAL_ERROR_MESSAGE = "Internal server error";
        String CRUISER_FIELDS_ERROR_MESSAGE = "Cruiser has to have 0-6 fields";
        String CRUISER_HAS_NO_DRIVE_ERROR_MESSAGE = "Cruiser has to have a drive";
        String CRUISER_HAS_NOT_ENOUGH_ENERGY_ERROR_MESSAGE = "Cruiser has not enough energy";
        String DREADNOUGHT_FIELDS_ERROR_MESSAGE = "Dreadnought has to have 0-6 fields";
        String DREADNOUGHT_HAS_NO_DRIVE_ERROR_MESSAGE = "Dreadnought has to have a drive";
        String DREADNOUGHT_HAS_NOT_ENOUGH_ENERGY_ERROR_MESSAGE = "Dreadnought has not enough energy";
        String INTERCEPTOR_FIELDS_ERROR_MESSAGE = "Interceptor has to have 0-6 fields";
        String INTERCEPTOR_HAS_NO_DRIVE_ERROR_MESSAGE = "Interceptor has to have a drive";
        String INTERCEPTOR_HAS_NOT_ENOUGH_ENERGY_ERROR_MESSAGE = "Interceptor has not enough energy";
        String COGNITO_OPERATION_TYPE_NOT_SUPPORTED_ERROR_MESSAGE = "Cognito operation type not supported";
        String REPETITION_NUMBER_CANNOT_BE_ZERO_ERROR_MESSAGE = "Repetitions number must be bigger then 0";
        String FLEETS_CANT_BE_EMPTY_ERROR_MESSAGE = "Fleet can't be empty";
        String FEATURE_FLAGS_OPERATION_TYPE_NOT_SUPPORTED_ERROR_MESSAGE = "Feature flags operation type not supported";
        String FEATURE_FLAGS_EMPTY_ERROR_MESSAGE = "Feature flags cannot be null";
        String TO_JSON_MAPPING_ERROR_MESSAGE = "An error occurred when mapping to json";
        String READING_FEATURE_FLAGS_ERROR_MESSAGE = "An error occurred when getting value of feature flags";
        String STRING_TO_OBJECT_MAPPING_ERROR_MESSAGE = "An error occurred when mapping string to object";
        String APP_CONFIG_DEPLOYMENT_STRATEGY_NOT_FOUND_ERROR_MESSAGE = "Deployment strategy not found";
        String APP_CONFIG_LATEST_CONFIGURATION_NOT_FOUND_ERROR_MESSAGE = "Latest configuration not found";
        String APP_CONFIG_FEATURE_DISABLED_ERROR_MESSAGE = "Feature is disabled";
        String APP_CONFIG_GETTING_FEATURE_FLAGS_ERROR_MESSAGE = "An error occurred when getting feature flags";
        String USERNAME_OR_PASSWORD_MISSING_ERROR_MESSAGE = "Username or password is missing";
        String UNEXPECTED_DATABASE_ERROR_MESSAGE = "Unexpected database error, please check logs";
        String UNEXPECTED_ERROR_WHEN_CLOSING_STATEMENT_MESSAGE = "Unexpected error occurred during closing statement";
        String UNEXPECTED_ERROR_WHEN_CLOSING_CONNECTION_MESSAGE = "Unexpected error occurred during closing connection";
        String UNEXPECTED_ERROR_WHEN_CLOSING_RESULT_SET_MESSAGE = "Unexpected error occurred during closing result set";
        String PLAYER_ALREADY_EXISTS_MESSAGE = "This player already exists in database";
        String GAME_ALREADY_EXISTS_MESSAGE = "This game already exists in database";
        String GAME_NOT_FOUND_MESSAGE = "Game not found";
        String PLAYER_NOT_FOUND_MESSAGE = "Player not found";
    }

    interface featureFlags
    {
        String APP_CONFIG_APPLICATION_ID = System.getenv("APP_CONFIG_APPLICATION_ID");
        String APP_CONFIG_APPLICATION_NAME = "ES";
        String APP_CONFIG_CONFIGURATION_PROFILE_ID = System.getenv("APP_CONFIG_CONFIGURATION_PROFILE_ID");
        String APP_CONFIG_ENVIRONMENT_ID = System.getenv("APP_CONFIG_ENVIRONMENT_ID");
        String APP_CONFIG_ENVIRONMENT_NAME = "ESAppConfigEnv";
        String APP_CONFIG_DEPLOYMENT_STRATEGY_NAME = "ESCustomDeploymentStrategy";
        String APP_CONFIG_PROFILE_NAME = "Features";
    }
}
