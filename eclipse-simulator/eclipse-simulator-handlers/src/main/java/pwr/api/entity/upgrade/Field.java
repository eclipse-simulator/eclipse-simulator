package pwr.api.entity.upgrade;

import lombok.Data;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

@Data
public abstract class Field
{
    private FieldType fieldType;
    private FieldName name;
    private int initiative = 0;
    private int hp = 10;
    private int hitChanceBuff = 0;
    private int hitChanceDeBuff = 0;
    private int requiredEnergy = 0;
    private int damage = 0;
}
