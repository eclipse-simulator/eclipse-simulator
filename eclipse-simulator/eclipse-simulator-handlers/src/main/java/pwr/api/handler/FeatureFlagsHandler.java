package pwr.api.handler;

import pwr.api.exception.ESApiException;
import pwr.api.feature.flags.FeatureFlagsHelper;
import pwr.api.request.FeatureFlagsRequestData;
import pwr.api.response.FeatureFlagsData;

import static pwr.api.constants.Constants.errors.FEATURE_FLAGS_EMPTY_ERROR_MESSAGE;
import static pwr.api.constants.Constants.errors.FEATURE_FLAGS_OPERATION_TYPE_NOT_SUPPORTED_ERROR_MESSAGE;
import static pwr.api.enums.ErrorCode.BAD_REQUEST_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

public class FeatureFlagsHandler extends BaseHandler<FeatureFlagsRequestData, FeatureFlagsData>
{
    @Override
    protected FeatureFlagsData processRequest(FeatureFlagsRequestData request)
    {
        FeatureFlagsData featureFlags = request.getFeatureFlags();
        FeatureFlagsHelper featureFlagsHelper = new FeatureFlagsHelper();

        switch (request.getOperationType())
        {
            case GET:
                featureFlags = featureFlagsHelper.getAllFeatureFlags();
                break;

            case UPDATE:
                if(featureFlags == null)
                    throw new ESApiException(ERROR, FEATURE_FLAGS_EMPTY_ERROR_MESSAGE, BAD_REQUEST_ERROR);
                featureFlagsHelper.updateFeatureFlags(featureFlags);
                break;

            default:
                throw new ESApiException(ERROR, FEATURE_FLAGS_OPERATION_TYPE_NOT_SUPPORTED_ERROR_MESSAGE, BAD_REQUEST_ERROR);
        }

        return featureFlags;
    }

    @Override
    protected void validateRequest(FeatureFlagsRequestData request)
    {

    }
}
