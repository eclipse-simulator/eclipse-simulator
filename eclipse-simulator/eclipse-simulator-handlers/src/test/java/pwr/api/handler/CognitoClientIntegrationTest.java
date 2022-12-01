package pwr.api.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pwr.api.cognito.CognitoClient;
import pwr.api.exception.BaseException;
import pwr.api.handler.helpers.LambdaMockContext;
import pwr.api.request.CognitoRequestData;
import pwr.api.response.BaseResponseData;
import pwr.api.response.CognitoResponseData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pwr.api.enums.CognitoOperationType.LOGIN;
import static pwr.api.enums.CognitoOperationType.REGISTER;

public class CognitoClientIntegrationTest
{
    private static Context context;
    private static CognitoHandler handler;
    private static CognitoClient client;
    private static final String TEST_USER_NAME = "INTEGRATION_TEST_USER_NAME";
    private static final String TEST_PASSWORD = "INTEGRATION_TEST_PASSWORD-1a";
    private static final String WRONG_TEST_USER_NAME = "WRONG_INTEGRATION_TEST_USER_NAME";
    private static final String WRONG_TEST_PASSWORD = "WRONG_INTEGRATION_TEST_PASSWORD-1a";
    private static final String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password";

    @BeforeAll
    static void loadConfigurationFromEnvironment()
    {
        context = new LambdaMockContext();
        handler = new CognitoHandler();
        client = new CognitoClient();
    }

    @Test
    public void shouldRegisterAndLoginUser()
    {
        CognitoRequestData registerRequest = new CognitoRequestData(REGISTER, TEST_USER_NAME, TEST_PASSWORD);
        CognitoRequestData loginRequest = new CognitoRequestData(LOGIN, TEST_USER_NAME, TEST_PASSWORD);

        try
        {
            handler.handleRequest(registerRequest, context);
            handler.handleRequest(loginRequest, context);
        } finally
        {
            client.deleteUser(TEST_USER_NAME);
        }
    }

    @Test
    public void shouldThrowAnExceptionWhenWrongUsernameIsUsed()
    {
        CognitoRequestData registerRequest = new CognitoRequestData(REGISTER, TEST_USER_NAME, TEST_PASSWORD);
        CognitoRequestData loginRequest = new CognitoRequestData(LOGIN, WRONG_TEST_USER_NAME, TEST_PASSWORD);

        try
        {
            handler.handleRequest(registerRequest, context);
            BaseResponseData<CognitoResponseData> response = handler.handleRequest(loginRequest, context);
            List<? super BaseException> errors = response.getErrors();
            assertNotEquals(0, errors.size());
            assertEquals(WRONG_USERNAME_OR_PASSWORD, ((BaseException) errors.get(0)).getMessage());
        } finally
        {
            client.deleteUser(TEST_USER_NAME);
        }
    }

    @Test
    public void shouldThrowAnExceptionWhenWrongPasswordIsUsed()
    {
        CognitoRequestData registerRequest = new CognitoRequestData(REGISTER, TEST_USER_NAME, TEST_PASSWORD);
        CognitoRequestData loginRequest = new CognitoRequestData(LOGIN, TEST_USER_NAME, WRONG_TEST_PASSWORD);

        try
        {
            handler.handleRequest(registerRequest, context);
            handler.handleRequest(loginRequest, context);
            BaseResponseData<CognitoResponseData> response = handler.handleRequest(loginRequest, context);
            List<? super BaseException> errors = response.getErrors();
            assertNotEquals(0, errors.size());
            assertEquals(WRONG_USERNAME_OR_PASSWORD, ((BaseException) errors.get(0)).getMessage());
        } finally
        {
            client.deleteUser(TEST_USER_NAME);
        }
    }
}
