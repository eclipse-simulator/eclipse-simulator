package pwr.api.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pwr.api.exception.ESApiException;

import static com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS;
import static pwr.api.constants.Constants.errors.STRING_TO_OBJECT_MAPPING_ERROR_MESSAGE;
import static pwr.api.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMapperUtil
{
    private static final ObjectMapper mapper;

    static
    {
        mapper = new ObjectMapper()
                .enable(ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature())
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static <T> T convertToObject(Object json, Class<T> valueType)
    {
        return mapper.convertValue(json, valueType);
    }

    public static String toJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public static <T> T stringToObject(String json, Class<T> valueType)
    {
        try
        {
            return mapper.readValue(json, valueType);
        } catch (Exception e)
        {
            throw new ESApiException(ERROR, STRING_TO_OBJECT_MAPPING_ERROR_MESSAGE, INTERNAL_SERVER_ERROR);
        }
    }

    public static JsonNode readTree(String string) throws JsonProcessingException
    {
        return mapper.readTree(string);
    }
}
