package pwr.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
public class BaseIntegrationTest
{
    @SystemStub
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();


    @BeforeAll
    public static void baseInit()
    {
        environmentVariables.set("APP_CONFIG_APPLICATION_ID", "pnq8atd");
        environmentVariables.set("APP_CONFIG_CONFIGURATION_PROFILE_ID", "xs5nqip");
        environmentVariables.set("APP_CONFIG_ENVIRONMENT_ID", "8ncrp1k");
        environmentVariables.set("COGNITO_USER_POOL_ID", "eu-west-2_ITyh0nFON");
        environmentVariables.set("COGNITO_USER_POOL_CLIENT_ID", "4me5a9qc8pquuc71ba4m0vr8sa");
    }
}
