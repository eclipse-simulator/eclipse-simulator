package pwr.api.entity.ship;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.entity.upgrade.*;
import pwr.api.exception.ESApiException;

import java.util.ArrayList;
import java.util.List;

import static pwr.api.constants.Constants.errors.*;
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
            throw new ESApiException(ERROR, DREADNOUGHT_FIELDS_ERROR_MESSAGE, BAD_REQUEST_ERROR);
        }


        if(fields.stream().noneMatch(field -> field.getFieldType().equals(DRIVE)))
        {
            throw new ESApiException(ERROR, DREADNOUGHT_HAS_NO_DRIVE_ERROR_MESSAGE, BAD_REQUEST_ERROR);
        }

        int energyBalance = 0;
        for(Field field: fields)
        {
            energyBalance += field.getEnergy();
        }
        if(energyBalance < 0)
        {
            throw new ESApiException(ERROR, DREADNOUGHT_HAS_NOT_ENOUGH_ENERGY_ERROR_MESSAGE, BAD_REQUEST_ERROR);
        }
    }
}
