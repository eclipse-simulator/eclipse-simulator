package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hull1 extends Field
{
    public Hull1()
    {
        super();
        setFieldType(FieldType.HULL);
        setName(FieldName.HULL);
        setHp(1);
    }
}
