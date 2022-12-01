package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.PLASMA_MISSILE;
import static pwr.api.enums.FieldType.MISSILE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Missile2 extends Field
{
    public Missile2()
    {
        super();
        setFieldType(MISSILE);
        setName(PLASMA_MISSILE);
        setEnergy(-1);
        setDamage(2);
    }
}
