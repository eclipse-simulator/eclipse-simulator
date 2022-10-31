package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hull2 extends Field
{
    FieldType fieldType = FieldType.HULL;
    FieldName name = FieldName.IMPROVED_HULL;
    int hp = 2;
}
