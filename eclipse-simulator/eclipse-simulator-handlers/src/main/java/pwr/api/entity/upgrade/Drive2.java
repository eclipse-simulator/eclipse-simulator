package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.FUSION_DRIVE;
import static pwr.api.enums.FieldType.DRIVE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Drive2 extends Field
{
    private FieldType fieldType = DRIVE;
    private FieldName name = FUSION_DRIVE;
    private int initiative = 2;
    private int requiredEnergy = 2;
}
