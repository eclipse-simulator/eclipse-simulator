package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.FLUX_MISSILE;
import static pwr.api.enums.FieldType.MISSILE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Missile1 extends Field
{
    public Missile1()
    {
        super();
        setFieldType(MISSILE);
        setName(FLUX_MISSILE);
        setDamage(1);
    }
}
