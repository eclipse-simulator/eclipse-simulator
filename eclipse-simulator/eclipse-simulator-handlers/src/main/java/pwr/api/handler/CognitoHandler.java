package pwr.api.handler;

import pwr.api.cognito.CognitoClient;
import pwr.api.enums.FeatureType;
import pwr.api.exception.ESApiException;
import pwr.api.feature.flags.FeatureFlagsHelper;
import pwr.api.request.CognitoRequestData;
import pwr.api.request.CredentialsData;
import pwr.api.response.CognitoResponseData;

import static pwr.api.constants.Constants.errors.COGNITO_OPERATION_TYPE_NOT_SUPPORTED_ERROR_MESSAGE;
import static pwr.api.constants.Constants.errors.USERNAME_OR_PASSWORD_MISSING_ERROR_MESSAGE;
import static pwr.api.enums.CognitoOperationType.LOGIN;
import static pwr.api.enums.CognitoOperationType.REGISTER;
import static pwr.api.enums.ErrorCode.BAD_REQUEST_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

public class CognitoHandler extends BaseHandler<CognitoRequestData, CognitoResponseData>
{
    @Override
    protected CognitoResponseData processRequest(CognitoRequestData request)
    {
        validateRequest(request);

        CredentialsData credentials = request.getCredentials();

        CognitoClient cognitoClient = new CognitoClient();

        if(request.getOperationType() == REGISTER)
        {
            FeatureFlagsHelper featureFlagsHelper = new FeatureFlagsHelper();
            featureFlagsHelper.checkIfFeatureIsEnabled(FeatureType.REGISTERING);
            return cognitoClient.register(credentials.getUsername(), credentials.getPassword());
        } else if(request.getOperationType() == LOGIN)
        {
            return cognitoClient.login(credentials.getUsername(), credentials.getPassword());
        }  else
        {
            throw new ESApiException(ERROR, COGNITO_OPERATION_TYPE_NOT_SUPPORTED_ERROR_MESSAGE, BAD_REQUEST_ERROR);
        }
    }

    @Override
    protected void validateRequest(CognitoRequestData request)
    {
        CredentialsData credentials = request.getCredentials();
        if(credentials.getUsername() == null || credentials.getPassword() == null)
        {
            throw new ESApiException(ERROR, USERNAME_OR_PASSWORD_MISSING_ERROR_MESSAGE, BAD_REQUEST_ERROR);
        }
    }
}
