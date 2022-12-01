package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.ELECTRON_COMPUTER;
import static pwr.api.enums.FieldType.COMPUTER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Computer3 extends Field
{
    public Computer3()
    {
        super();
        setFieldType(COMPUTER);
        setName(ELECTRON_COMPUTER);
        setHitChanceBuff(3);
        setEnergy(-2);
    }
}
