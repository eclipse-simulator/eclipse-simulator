package pwr.api.entity.ship;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.entity.upgrade.*;
import pwr.api.exception.BaseException;

import java.util.ArrayList;
import java.util.List;

import static pwr.api.constants.Constants.ships.DREADNOUGHT_FIELDS_LIMIT;
import static pwr.api.enums.ErrorCode.BAD_REQUEST_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;
import static pwr.api.enums.FieldType.DRIVE;
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

    @Override
    public void validate() {
        List<Field> fields = getFields();
        if(fields.isEmpty() || fields.size() > DREADNOUGHT_FIELDS_LIMIT)
        {
            throw new BaseException(ERROR, "Dreadnought has to have 0-8 fields", BAD_REQUEST_ERROR);
        }


        if(fields.stream().noneMatch(field -> field.getFieldType().equals(DRIVE)))
        {
            throw new BaseException(ERROR, "Dreadnought has to have a drive", BAD_REQUEST_ERROR);
        }

        int energyBalance = 0;
        for(Field field: fields)
        {
            energyBalance += field.getEnergy();
        }
        if(energyBalance < 0)
        {
            throw new BaseException(ERROR, "Dreadnought has not enough energy", BAD_REQUEST_ERROR);
        }
    }
}
