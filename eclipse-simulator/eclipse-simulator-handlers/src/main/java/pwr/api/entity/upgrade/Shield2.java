package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.PHASE_SHIELD;
import static pwr.api.enums.FieldType.SHIELD;

@Data
@EqualsAndHashCode(callSuper = true)
public class Shield2 extends Field
{
    public Shield2()
    {
        super();
        setFieldType(SHIELD);
        setName(PHASE_SHIELD);
        setHitChanceDeBuff(2);
        setEnergy(-1);
    }
}
