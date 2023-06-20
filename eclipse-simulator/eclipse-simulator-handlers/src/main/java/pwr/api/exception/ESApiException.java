package pwr.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.ErrorCode;
import pwr.api.enums.ExceptionType;

@EqualsAndHashCode(callSuper = true)
@Data
public class ESApiException extends RuntimeException
{
    public ExceptionType exceptionType;

    public ErrorCode errorCode;

    public ESApiException(ExceptionType exceptionType, ErrorCode errorCode)
    {
        super(errorCode.getDefaultMessage());
        this.exceptionType = exceptionType;
        this.errorCode = errorCode;
    }

    public ESApiException(ExceptionType exceptionType, String message, ErrorCode errorCode)
    {
        super(message);
        this.exceptionType = exceptionType;
        this.errorCode = errorCode;
    }
}
