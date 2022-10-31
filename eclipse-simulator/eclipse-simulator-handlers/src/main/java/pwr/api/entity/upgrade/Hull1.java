package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hull1 extends Field
{
    private FieldType fieldType = FieldType.HULL;
    private FieldName name = FieldName.HULL;
    private int hp = 1;
}
