package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.SOLITON_CANNON;
import static pwr.api.enums.FieldType.CANNON;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cannon3 extends Field
{
    public Cannon3()
    {
        super();
        setFieldType(CANNON);
        setName(SOLITON_CANNON);
        setEnergy(-3);
        setDamage(3);
    }
}
