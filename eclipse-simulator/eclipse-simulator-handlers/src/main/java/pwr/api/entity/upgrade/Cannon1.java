package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.ION_CANNON;
import static pwr.api.enums.FieldType.CANNON;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cannon1 extends Field
{
    public Cannon1()
    {
        super();
        setFieldType(CANNON);
        setName(ION_CANNON);
        setEnergy(-1);
        setDamage(1);
    }
}
