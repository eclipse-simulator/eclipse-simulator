package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.ELECTRON_COMPUTER;
import static pwr.api.enums.FieldType.COMPUTER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Computer3 extends Field
{
    private FieldType fieldType = COMPUTER;
    private FieldName name = ELECTRON_COMPUTER;
    private int hitChanceBuff = 3;
    private int requiredEnergy = 2;
}
