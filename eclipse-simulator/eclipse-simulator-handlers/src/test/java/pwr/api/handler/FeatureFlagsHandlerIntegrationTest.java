package pwr.api.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pwr.api.BaseIntegrationTest;
import pwr.api.handler.helpers.LambdaMockContext;
import pwr.api.request.FeatureFlagsRequestData;
import pwr.api.response.BaseResponseData;
import pwr.api.response.FeatureFlagsData;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pwr.api.enums.FeatureFlagsOperationType.GET;
import static pwr.api.enums.FeatureFlagsOperationType.UPDATE;

public class FeatureFlagsHandlerIntegrationTest extends BaseIntegrationTest
{
    private static Context context;
    private static FeatureFlagsHandler handler;

    @BeforeAll
    static void loadConfigurationFromEnvironment()
    {
        context = new LambdaMockContext();
        handler = new FeatureFlagsHandler();;
    }

    @Test
    public void shouldGetAllFlags()
    {
        FeatureFlagsRequestData request = new FeatureFlagsRequestData();
        request.setOperationType(GET);
        BaseResponseData<FeatureFlagsData> response = handler.handleRequest(request, context);
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void shouldUpdateFlagsUsingCurrentFlagValues()
    {
        FeatureFlagsRequestData request = new FeatureFlagsRequestData();
        request.setOperationType(GET);
        BaseResponseData<FeatureFlagsData> getResponse = handler.handleRequest(request, context);
        FeatureFlagsData featureFlags = getResponse.getResponse();
        request.setOperationType(UPDATE);
        request.setFeatureFlags(featureFlags);
        BaseResponseData<FeatureFlagsData> updateResponse = handler.handleRequest(request, context);
        assertTrue(getResponse.getErrors().isEmpty());
        assertTrue(updateResponse.getErrors().isEmpty());
    }
}
