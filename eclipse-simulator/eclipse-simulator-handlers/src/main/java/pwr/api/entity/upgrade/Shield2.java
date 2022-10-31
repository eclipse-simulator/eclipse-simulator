package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.PHASE_SHIELD;
import static pwr.api.enums.FieldType.SHIELD;

@Data
@EqualsAndHashCode(callSuper = true)
public class Shield2 extends Field
{
    FieldType fieldType = SHIELD;
    FieldName name = PHASE_SHIELD;
    int hitChanceDeBuff = 2;
    int requiredEnergy = 1;
}
