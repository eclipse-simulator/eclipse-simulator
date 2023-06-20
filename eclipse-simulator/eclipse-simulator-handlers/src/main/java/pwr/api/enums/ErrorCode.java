package pwr.api.enums;

public enum ErrorCode
{
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error"),
    BAD_REQUEST_ERROR("BAD_REQUEST_ERROR", "Bad request error"),
    UNAUTHORIZED_ERROR("UNAUTHORIZED", "Unauthorized error"),
    FEATURE_DISABLED("FEATURE_DISABLED", "Feature disabled"),
    NOT_FOUND_ERROR("NOT_FOUND", "Not found");

    private final String errorCode;
    private final String defaultMessage;

    ErrorCode()
    {
        this.errorCode = "INTERNAL_SERVER_ERROR";
        this.defaultMessage = "Internal server error";
    }


    ErrorCode(String errorCode, String defaultMessage)
    {
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public String getDefaultMessage()
    {
        return defaultMessage;
    }
}
