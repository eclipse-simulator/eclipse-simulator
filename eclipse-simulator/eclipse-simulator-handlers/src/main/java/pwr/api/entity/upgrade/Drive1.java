package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.NUCLEAR_DRIVE;
import static pwr.api.enums.FieldType.DRIVE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Drive1 extends Field
{
    private FieldType fieldType = DRIVE;
    private FieldName name = NUCLEAR_DRIVE;
    private int initiative = 1;
    private int requiredEnergy = 1;
}
