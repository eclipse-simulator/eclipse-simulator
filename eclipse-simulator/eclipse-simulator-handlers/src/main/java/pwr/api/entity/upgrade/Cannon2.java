package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.ION_CANNON;
import static pwr.api.enums.FieldName.PLASMA_CANNON;
import static pwr.api.enums.FieldType.CANNON;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cannon2 extends Field
{
    public Cannon2()
    {
        super();
        setFieldType(CANNON);
        setName(PLASMA_CANNON);
        setEnergy(-2);
        setDamage(2);
    }
}
