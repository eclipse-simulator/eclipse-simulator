package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.GAUSS_SHIELD;
import static pwr.api.enums.FieldType.SHIELD;

@Data
@EqualsAndHashCode(callSuper = true)
public class Shield1 extends Field
{
    FieldType fieldType = SHIELD;
    FieldName name = GAUSS_SHIELD;
    int hitChanceDeBuff = 1;
}
