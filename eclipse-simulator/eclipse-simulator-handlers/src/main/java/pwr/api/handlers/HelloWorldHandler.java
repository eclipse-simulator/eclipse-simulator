package pwr.api.handlers;

import pwr.api.response.BaseResponseData;
import pwr.api.request.BaseRequestData;

public class HelloWorldHandler extends GenericHandler<BaseRequestData, BaseResponseData>
{
    public BaseResponseData handleRequest(BaseRequestData request)
    {
        String message = "Hello " + request.getMessage();
        return new BaseResponseData(message);
    }
}
