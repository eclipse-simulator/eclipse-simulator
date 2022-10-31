package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.FLUX_MISSILE;
import static pwr.api.enums.FieldType.MISSILE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Missile1 extends Field
{
    FieldType fieldType = MISSILE;
    FieldName name = FLUX_MISSILE;
    int initiative = 1;
    int damage = 1;
}
