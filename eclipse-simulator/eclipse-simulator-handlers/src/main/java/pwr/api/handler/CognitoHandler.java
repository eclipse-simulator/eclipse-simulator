package pwr.api.handler;

import pwr.api.cognito.CognitoClient;
import pwr.api.exception.BaseException;
import pwr.api.request.CognitoRequestData;
import pwr.api.response.CognitoResponseData;

import static pwr.api.enums.CognitoOperationType.LOGIN;
import static pwr.api.enums.CognitoOperationType.REGISTER;
import static pwr.api.enums.ErrorCode.BAD_REQUEST_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

public class CognitoHandler extends BaseHandler<CognitoRequestData, CognitoResponseData>
{
    private static final String COGNITO_OPERATION_TYPE_NOT_SUPPORTED = "Cognito operation type not supported";

    @Override
    protected CognitoResponseData processRequest(CognitoRequestData request)
    {
        CognitoClient cognitoClient = new CognitoClient();

        if(request.getOperationType() == REGISTER)
        {
            return cognitoClient.register(request.getUserName(), request.getPassword());
        } else if(request.getOperationType() == LOGIN)
        {
            return cognitoClient.login(request.getUserName(), request.getPassword());
        }  else
        {
            throw new BaseException(ERROR, COGNITO_OPERATION_TYPE_NOT_SUPPORTED, BAD_REQUEST_ERROR);
        }
    }

    @Override
    protected void validateRequest(CognitoRequestData request)
    {

    }
}
