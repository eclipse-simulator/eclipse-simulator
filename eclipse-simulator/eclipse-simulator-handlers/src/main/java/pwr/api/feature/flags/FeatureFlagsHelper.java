package pwr.api.feature.flags;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import pwr.api.enums.FeatureType;
import pwr.api.exception.ESApiException;
import pwr.api.response.FeatureFlagsData;
import pwr.api.utils.JsonMapperUtil;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.*;
import software.amazon.awssdk.services.appconfigdata.AppConfigDataClient;
import software.amazon.awssdk.services.appconfigdata.model.GetLatestConfigurationRequest;
import software.amazon.awssdk.services.appconfigdata.model.GetLatestConfigurationResponse;
import software.amazon.awssdk.services.appconfigdata.model.StartConfigurationSessionRequest;
import software.amazon.awssdk.services.appconfigdata.model.StartConfigurationSessionResponse;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static pwr.api.constants.Constants.errors.*;
import static pwr.api.constants.Constants.featureFlags.*;
import static pwr.api.enums.ErrorCode.FEATURE_DISABLED;
import static pwr.api.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

public class FeatureFlagsHelper
{
    private final AppConfigDataClient dataClient;
    private final AppConfigClient client;
    private static final String TEXT_PLAIN = "text/plain";

    public FeatureFlagsHelper()
    {
        this.dataClient  = AppConfigDataClient.builder().region(Region.EU_WEST_2).build();
        this.client = AppConfigClient.builder().region(Region.EU_WEST_2).build();
    }

    public void updateFeatureFlags(FeatureFlagsData newFeatureFlags)
    {
        String deploymentStrategyId = getDeploymentStrategyId();
        int version = getLatestVersionNumber();
        int versionToBeDeployed = version;
        FeatureFlagsData latestConfiguration = getAllFeatureFlags();
        if (!newFeatureFlags.equals(latestConfiguration))
        {
            versionToBeDeployed++;
        }
        String featureFlags;
        try
        {
            featureFlags = JsonMapperUtil.toJson(newFeatureFlags);
        } catch (Exception e)
        {
            throw new ESApiException(ERROR, TO_JSON_MAPPING_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
        SdkBytes content = SdkBytes.fromUtf8String(featureFlags);

        CreateHostedConfigurationVersionResponse configurationResponse =
                client.createHostedConfigurationVersion(CreateHostedConfigurationVersionRequest.builder()
                        .applicationId(APP_CONFIG_APPLICATION_ID)
                        .configurationProfileId(APP_CONFIG_CONFIGURATION_PROFILE_ID)
                        .content(content)
                        .contentType(TEXT_PLAIN)
                        .latestVersionNumber(version)
                        .build());

        System.out.println(configurationResponse);

        deploy(deploymentStrategyId, versionToBeDeployed);
    }

    public FeatureFlagsData getAllFeatureFlags()
    {
        String response = getConfiguration().configuration().asUtf8String();
        JsonNode jsonNode;
        try
        {
            jsonNode = JsonMapperUtil.readTree(response);
        } catch (Exception e)
        {
            throw new ESApiException(ERROR, READING_FEATURE_FLAGS_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
        return JsonMapperUtil.stringToObject(jsonNode.toString(), FeatureFlagsData.class);
    }

    public void checkIfFeatureIsEnabled(FeatureType featureType)
    {
        try
        {
            if(!getBooleanValue(getConfiguration(), featureType))
            {
                throw new ESApiException(ERROR, APP_CONFIG_FEATURE_DISABLED_ERROR_MESSAGE, FEATURE_DISABLED);
            }
        } catch (Exception e)
        {
            throw new ESApiException(ERROR, APP_CONFIG_GETTING_FEATURE_FLAGS_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
    }

    private String getDeploymentStrategyId()
    {
        ListDeploymentStrategiesResponse deploymentStrategiesResponse =
                client.listDeploymentStrategies(ListDeploymentStrategiesRequest.builder().build());

        Optional<DeploymentStrategy> appConfigDeploymentStrategy  = deploymentStrategiesResponse
                .items()
                .stream()
                .filter(deploymentStrategy -> Objects.equals(deploymentStrategy.name(),
                        APP_CONFIG_DEPLOYMENT_STRATEGY_NAME))
                .findFirst();

        if(appConfigDeploymentStrategy.isPresent())
        {
            return appConfigDeploymentStrategy.get().id();
        } else
        {
            throw new ESApiException(ERROR, APP_CONFIG_DEPLOYMENT_STRATEGY_NOT_FOUND_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
    }

    private int getLatestVersionNumber()
    {
        ListHostedConfigurationVersionsResponse configurationsResponse =
                client.listHostedConfigurationVersions(ListHostedConfigurationVersionsRequest.builder()
                        .applicationId(APP_CONFIG_APPLICATION_ID)
                        .configurationProfileId(APP_CONFIG_CONFIGURATION_PROFILE_ID)
                        .build());

        Optional<HostedConfigurationVersionSummary> latestConfiguration =
                configurationsResponse
                        .items()
                        .stream()
                        .max(Comparator.comparing(HostedConfigurationVersionSummary::versionNumber));

        if(latestConfiguration.isPresent())
        {
            return latestConfiguration.get().versionNumber();
        } else
        {
            throw new ESApiException(ERROR, APP_CONFIG_LATEST_CONFIGURATION_NOT_FOUND_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
    }

    private GetLatestConfigurationResponse getConfiguration()
    {
        StartConfigurationSessionResponse response = this.dataClient.startConfigurationSession(
                StartConfigurationSessionRequest.builder()
                        .applicationIdentifier(APP_CONFIG_APPLICATION_NAME)
                        .environmentIdentifier(APP_CONFIG_ENVIRONMENT_NAME)
                        .configurationProfileIdentifier(APP_CONFIG_PROFILE_NAME)
                        .build()
        );
        return this.dataClient.getLatestConfiguration(GetLatestConfigurationRequest.builder()
                .configurationToken(response.initialConfigurationToken())
                .build());
    }


    private void deploy(String deploymentStrategyId, int version)
    {
        StartDeploymentResponse response = client.startDeployment(StartDeploymentRequest.builder()
                .applicationId(APP_CONFIG_APPLICATION_ID)
                .configurationProfileId(APP_CONFIG_CONFIGURATION_PROFILE_ID)
                .environmentId(APP_CONFIG_ENVIRONMENT_ID)
                .deploymentStrategyId(deploymentStrategyId)
                .configurationVersion(String.valueOf(version))
                .build());

        System.out.println(response);
    }

    private boolean getBooleanValue(GetLatestConfigurationResponse configuration, FeatureType featureType) throws JsonProcessingException {
        String response = configuration.configuration().asUtf8String();
        JsonNode jsonNode = JsonMapperUtil.readTree(response);
        JsonNode featureNode = jsonNode.get(featureType.getFeatureName());
        if(featureNode == null)
        {
            System.out.println("Something went wrong, taking feature flag default value");
            return featureType.getDefaultValue();
        }
        String flagValue = featureNode.asText();
        return Boolean.parseBoolean(flagValue);
    }
}
