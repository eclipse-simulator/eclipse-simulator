package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.ANTIMATTER_CANNON;
import static pwr.api.enums.FieldType.CANNON;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cannon4 extends Field
{
    private FieldType fieldType = CANNON;
    private FieldName name = ANTIMATTER_CANNON;
    private int requiredEnergy = 4;
    private int damage = 4;
}
