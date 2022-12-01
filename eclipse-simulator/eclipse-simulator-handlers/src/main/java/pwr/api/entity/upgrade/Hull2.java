package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.IMPROVED_HULL;
import static pwr.api.enums.FieldType.HULL;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hull2 extends Field
{
    public Hull2()
    {
        super();
        setFieldType(HULL);
        setName(IMPROVED_HULL);
        setHp(2);
        setEnergy(-1);
    }
}
