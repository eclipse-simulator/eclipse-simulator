package pwr.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pwr.api.exception.ESApiException;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseData<R>
{
    R response;
    private List<? super ESApiException> errors = new ArrayList<>();
}
