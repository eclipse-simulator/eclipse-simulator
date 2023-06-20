package pwr.api.cognito;

import pwr.api.exception.ESApiException;
import pwr.api.response.CognitoResponseData;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.HashMap;
import java.util.Map;

import static pwr.api.constants.Constants.aws.COGNITO_CLIENT_ID;
import static pwr.api.constants.Constants.aws.COGNITO_USER_POOL_ID;
import static pwr.api.constants.Constants.errors.*;
import static pwr.api.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static pwr.api.enums.ErrorCode.UNAUTHORIZED_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

public class CognitoClient
{
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String USER_GROUP_NAME = "USER";

    private final CognitoIdentityProviderClient client;

    public CognitoClient()
    {
        client = CognitoIdentityProviderClient.builder()
                .region(Region.EU_WEST_2)
                .build();
    }

    public CognitoResponseData register(String username, String password)
    {
        SignUpRequest request = SignUpRequest
                .builder()
                .clientId(COGNITO_CLIENT_ID)
                .username(username)
                .password(password)
                .build();

        client.signUp(request);

        AdminConfirmSignUpRequest confirmRequest = AdminConfirmSignUpRequest
                .builder()
                .username(username)
                .userPoolId(COGNITO_USER_POOL_ID)
                .build();

        client.adminConfirmSignUp(confirmRequest);

        AdminAddUserToGroupRequest addUserToGroupRequest = AdminAddUserToGroupRequest
                .builder()
                .username(username)
                .userPoolId(COGNITO_USER_POOL_ID)
                .groupName(USER_GROUP_NAME)
                .build();

        client.adminAddUserToGroup(addUserToGroupRequest);

        return new CognitoResponseData(true, null);
    }

    public CognitoResponseData login(String username, String password)
    {
        try
        {
            Map<String,String> authParameters = new HashMap<>();
            authParameters.put(USERNAME, username);
            authParameters.put(PASSWORD, password);

            InitiateAuthRequest request = InitiateAuthRequest
                    .builder()
                    .clientId(COGNITO_CLIENT_ID)
                    .authParameters(authParameters)
                    .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                    .build();

            AuthenticationResultType response = client.initiateAuth(request).authenticationResult();

            return new CognitoResponseData(true, response.accessToken());
        } catch (Exception e)
        {
            if((e.getMessage().contains(WRONG_USERNAME_OR_PASSWORD_ERROR_MESSAGE)) ||
                    e.getMessage().contains(USER_DOES_NOT_EXIST_ERROR_MESSAGE))
            {
                throw new ESApiException(ERROR, AUTHENTICATION_ERROR_MESSAGE, UNAUTHORIZED_ERROR);
            } else
            {
                throw new ESApiException(ERROR, INTERNAL_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
            }
        }
    }

    public void deleteUser(String username)
    {
        AdminDeleteUserRequest request = AdminDeleteUserRequest
                .builder()
                .userPoolId(COGNITO_USER_POOL_ID)
                .username(username)
                .build();
        client.adminDeleteUser(request);
    }
}
