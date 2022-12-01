package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.ANTIMATTER_CANNON;
import static pwr.api.enums.FieldType.CANNON;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cannon4 extends Field
{
    public Cannon4()
    {
        super();
        setFieldType(CANNON);
        setName(ANTIMATTER_CANNON);
        setEnergy(-4);
        setDamage(4);
    }
}
