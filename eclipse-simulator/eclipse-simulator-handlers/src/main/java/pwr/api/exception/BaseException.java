package pwr.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.ErrorCode;
import pwr.api.enums.ExceptionType;

import static pwr.api.enums.ErrorCode.INTERNAL_SERVER_ERROR;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException
{
    public ExceptionType type;

    public ErrorCode code = INTERNAL_SERVER_ERROR;

    public BaseException(ExceptionType exceptionType, String message)
    {
        super(message);
        this.type = exceptionType;
    }
}
