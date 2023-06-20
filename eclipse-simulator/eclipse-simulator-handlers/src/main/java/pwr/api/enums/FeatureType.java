package pwr.api.enums;

public enum FeatureType
{
    REGISTERING("registering", true);

    private final String featureName;
    private final boolean defaultValue;

    FeatureType(String featureName, boolean defaultValue)
    {
        this.featureName = featureName;
        this.defaultValue = defaultValue;
    }

    public String getFeatureName()
    {
        return featureName;
    }

    public boolean getDefaultValue()
    {
        return defaultValue;
    }
}
