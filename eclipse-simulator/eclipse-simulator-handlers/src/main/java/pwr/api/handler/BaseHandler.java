package pwr.api.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import pwr.api.exception.BaseException;
import pwr.api.response.BaseResponseData;

import java.lang.reflect.ParameterizedType;

import static pwr.api.enums.ExceptionType.ERROR;
import static pwr.api.utils.JsonMapperUtil.convertToObject;

public abstract class BaseHandler<R, O> implements RequestHandler<Object, BaseResponseData<O>> {
    private Class<R> requestType;
    private boolean initialized;

    @SuppressWarnings("unchecked")
    protected void init()
    {
        if (!initialized)
        {
            this.requestType = (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            this.initialized = true;
        }
    }

    protected abstract O processRequest(R request);

    protected abstract void validateRequest(R request);

    @Override
    public BaseResponseData<O> handleRequest(Object requestObject, Context context)
    {
        this.init();
        final R request = convertToObject(requestObject, requestType);
        BaseResponseData<O> response = new BaseResponseData<>();
        try
        {
            response.setResponse(processRequest(request));
            return response;
        } catch (BaseException e)
        {
            System.err.println(e.getMessage());
            response.getErrors().add(e);
            return response;
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            response.getErrors()
                    .add(new BaseException(ERROR, e.getMessage()));
            return response;
        }
    }
}
