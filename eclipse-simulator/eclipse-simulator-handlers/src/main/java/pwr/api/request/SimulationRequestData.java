package pwr.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pwr.api.dto.FleetDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimulationRequestData extends BaseRequestData
{
    private FleetDTO firstPlayerFleet;
    private FleetDTO secondPlayerFleet;
    private int repetitions;
}
