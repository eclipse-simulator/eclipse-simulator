package pwr.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pwr.api.enums.CognitoOperationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CognitoRequestData extends BaseRequestData
{
    private CognitoOperationType operationType;
    private CredentialsData credentials;
}
