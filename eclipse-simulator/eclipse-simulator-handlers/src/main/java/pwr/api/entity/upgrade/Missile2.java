package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.PLASMA_MISSILE;
import static pwr.api.enums.FieldType.MISSILE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Missile2 extends Field
{
    FieldType fieldType = MISSILE;
    FieldName name = PLASMA_MISSILE;
    int requiredEnergy = 1;
    int damage = 2;
}
