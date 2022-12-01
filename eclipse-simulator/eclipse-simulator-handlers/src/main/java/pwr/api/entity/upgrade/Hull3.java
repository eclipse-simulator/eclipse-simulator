package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.CONIFOLD_FIELD;
import static pwr.api.enums.FieldType.HULL;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hull3 extends Field
{
    public Hull3()
    {
        super();
        setFieldType(HULL);
        setName(CONIFOLD_FIELD);
        setHp(3);
        setEnergy(-2);
    }
}
