package pwr.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pwr.api.enums.RDSOperationType;
import pwr.api.statistics.Game;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RDSRequestData extends BaseRequestData
{
    private RDSOperationType operationType;
    private List<String> gameNames;
    private List<String> playerNames;
    private Game game;
}
