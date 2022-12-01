package pwr.api.entity.upgrade;

import lombok.Data;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

@Data
public abstract class Field
{
    private FieldType fieldType;
    private FieldName name;
    private int initiative;
    private int hp;
    private int hitChanceBuff;
    private int hitChanceDeBuff;
    private int energy;
    private int damage;
    public Field()
    {
        initiative = 0;
        hp = 10;
        hitChanceBuff = 0;
        hitChanceDeBuff = 0;
        energy = 0;
        damage = 0;
    }
}
