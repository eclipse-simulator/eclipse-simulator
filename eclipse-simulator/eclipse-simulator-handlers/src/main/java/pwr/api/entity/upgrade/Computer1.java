package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.GLUON_COMPUTER;
import static pwr.api.enums.FieldType.COMPUTER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Computer1 extends Field
{
    private FieldType fieldType = COMPUTER;
    private FieldName name = GLUON_COMPUTER;
    private int hitChanceBuff = 1;
}
