package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.FUSION_DRIVE;
import static pwr.api.enums.FieldType.DRIVE;

@Data
@EqualsAndHashCode(callSuper = true)
public class Drive2 extends Field
{
    public Drive2()
    {
        super();
        setFieldType(DRIVE);
        setName(FUSION_DRIVE);
        setInitiative(2);
        setEnergy(-2);
    }
}
