package pwr.api.entity.ship;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.entity.upgrade.*;

import java.util.ArrayList;

import static pwr.api.enums.ShipType.CRUISER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cruiser extends Ship
{
    public Cruiser()
    {
        super();
        setShipType(CRUISER);
        setFields(new ArrayList<>());
        getFields().add(new Cannon1());
        getFields().add(new Computer1());
        getFields().add(new Hull1());
        getFields().add(new Drive1());
    }
}
