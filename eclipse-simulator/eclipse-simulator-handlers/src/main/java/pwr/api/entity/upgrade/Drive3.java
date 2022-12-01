package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.TACHYON_DRIVE;
import static pwr.api.enums.FieldType.DRIVE;

// TODO check values
@Data
@EqualsAndHashCode(callSuper = true)
public class Drive3 extends Field
{
    public Drive3()
    {
        super();
        setFieldType(DRIVE);
        setName(TACHYON_DRIVE);
        setInitiative(3);
        setEnergy(-3);
    }
}
