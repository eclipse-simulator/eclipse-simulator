package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.ION_CANNON;
import static pwr.api.enums.FieldType.CANNON;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cannon1 extends Field
{
    private FieldType fieldType = CANNON;
    private FieldName name = ION_CANNON;
    private int requiredEnergy = 1;
    private int damage = 1;
}
