package pwr.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FleetDTO
{
    private static final String DOLLAR_SIGN = "$";
    private static final String COLON = ":";
    private static final String HASH = "#";

    List<ShipDTO> ships;

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for(ShipDTO ship: ships)
        {
            result
                    .append(DOLLAR_SIGN)
                    .append(ship.shipType)
                    .append(COLON);
            for(String field: ship.getFields())
            {
                result
                        .append(field)
                        .append(HASH);
            }
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof FleetDTO)) return false;
        FleetDTO fleetDTO = (FleetDTO) o;
        return Objects.equals(getShips(), fleetDTO.getShips());
    }

    @Override
    public int hashCode()
    {
        if(!ships.isEmpty())
        {
            ships.sort((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getShipType(), o2.getShipType()));
            for(ShipDTO ship: ships)
            {
                ship.getFields().sort(String.CASE_INSENSITIVE_ORDER);
            }
        }
        return Objects.hash(getShips());
    }
}
