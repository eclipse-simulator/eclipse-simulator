package pwr.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pwr.api.response.FeatureFlagsData;
import pwr.api.enums.FeatureFlagsOperationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeatureFlagsRequestData extends BaseRequestData
{
    private FeatureFlagsOperationType operationType;

    private FeatureFlagsData featureFlags;
}
