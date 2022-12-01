package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.POSITRON_COMPUTER;
import static pwr.api.enums.FieldType.COMPUTER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Computer2 extends Field
{
    public Computer2()
    {
        super();
        setFieldType(COMPUTER);
        setName(POSITRON_COMPUTER);
        setHitChanceBuff(2);
        setEnergy(-1);
    }
}
