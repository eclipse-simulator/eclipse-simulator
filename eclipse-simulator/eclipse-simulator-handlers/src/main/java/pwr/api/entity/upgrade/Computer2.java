package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.POSITRON_COMPUTER;
import static pwr.api.enums.FieldType.COMPUTER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Computer2 extends Field
{
    private FieldType fieldType = COMPUTER;
    private FieldName name = POSITRON_COMPUTER;
    private int hitChanceBuff = 2;
    private int requiredEnergy = 1;
}
