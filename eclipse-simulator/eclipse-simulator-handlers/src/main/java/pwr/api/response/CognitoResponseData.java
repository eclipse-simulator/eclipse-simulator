package pwr.api.response;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@With
public class CognitoResponseData
{
    private boolean success;
    private String token;
}
