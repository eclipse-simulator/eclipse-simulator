package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.TACHYON_DRIVE;
import static pwr.api.enums.FieldType.DRIVE;

// TODO check values
@Data
@EqualsAndHashCode(callSuper = true)
public class Drive3 extends Field
{
    private FieldType fieldType = DRIVE;
    private FieldName name = TACHYON_DRIVE;
    private int initiative = 3;
    private int requiredEnergy = 3;
}
