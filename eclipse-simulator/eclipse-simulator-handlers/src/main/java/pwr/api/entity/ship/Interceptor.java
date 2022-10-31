package pwr.api.entity.ship;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.entity.upgrade.Cannon1;
import pwr.api.entity.upgrade.Drive1;

import java.util.ArrayList;

import static pwr.api.enums.ShipType.INTERCEPTOR;

@Data
@EqualsAndHashCode(callSuper = true)
public class Interceptor extends Ship
{
    public Interceptor()
    {
        super();
        setShipType(INTERCEPTOR);
        setFields(new ArrayList<>());
        getFields().add(new Cannon1());
        getFields().add(new Drive1());
    }
}
