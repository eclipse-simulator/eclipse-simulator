package pwr.api.entity.ship;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.entity.upgrade.*;

import java.util.ArrayList;

import static pwr.api.enums.ShipType.DREADNOUGHT;

@Data
@EqualsAndHashCode(callSuper = true)
public class Dreadnought extends Ship
{
    public Dreadnought()
    {
        super();
        setShipType(DREADNOUGHT);
        setFields(new ArrayList<>());
        getFields().add(new Cannon1());
        getFields().add(new Cannon1());
        getFields().add(new Computer1());
        getFields().add(new Hull1());
        getFields().add(new Hull1());
        getFields().add(new Drive1());
    }
}
