package pwr.api.handler.helpers;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lombok.Data;

@Data
public class LambdaMockContext implements Context
{
    private static final String EXAMPLE = "EXAMPLE";

    private String awsRequestId = EXAMPLE;
    private ClientContext clientContext;
    private String functionName = EXAMPLE;
    private CognitoIdentity identity;
    private String logGroupName = EXAMPLE;
    private String logStreamName = EXAMPLE;
    private LambdaLogger logger = new TestLogger();
    private int memoryLimitInMB = 128;
    private int remainingTimeInMillis = 15000;
    private String functionVersion = "0.0.0";
    private String invokedFunctionArn = "arn:aws:lambda:foo:123456789012:function:EXAMPLE";

    public LambdaMockContext() {}

    private static class TestLogger implements LambdaLogger
    {
        private TestLogger() {}

        public void log(String message)
        {
            System.err.println(message);
        }
    }
}
