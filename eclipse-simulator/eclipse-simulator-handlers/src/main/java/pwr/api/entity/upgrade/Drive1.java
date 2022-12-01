package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.NUCLEAR_DRIVE;
import static pwr.api.enums.FieldType.DRIVE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Drive1 extends Field
{
    public Drive1()
    {
        super();
        setFieldType(DRIVE);
        setName(NUCLEAR_DRIVE);
        setInitiative(1);
        setEnergy(-1);
    }
}
