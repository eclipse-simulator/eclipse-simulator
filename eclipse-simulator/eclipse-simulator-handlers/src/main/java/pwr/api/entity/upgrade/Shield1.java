package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.GAUSS_SHIELD;
import static pwr.api.enums.FieldType.SHIELD;

@Data
@EqualsAndHashCode(callSuper = true)
public class Shield1 extends Field
{
    public Shield1()
    {
        super();
        setFieldType(SHIELD);
        setName(GAUSS_SHIELD);
        setHitChanceDeBuff(1);
    }
}
