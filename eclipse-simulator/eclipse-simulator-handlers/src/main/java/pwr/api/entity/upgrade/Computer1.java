package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.GLUON_COMPUTER;
import static pwr.api.enums.FieldType.COMPUTER;

@Data
@EqualsAndHashCode(callSuper = true)
public class Computer1 extends Field
{
    public Computer1()
    {
        super();
        setFieldType(COMPUTER);
        setName(GLUON_COMPUTER);
        setHitChanceBuff(1);
    }
}
