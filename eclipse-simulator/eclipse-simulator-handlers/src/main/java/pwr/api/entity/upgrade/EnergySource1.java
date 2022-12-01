package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pwr.api.enums.FieldName;
import pwr.api.enums.FieldType;

import static pwr.api.enums.FieldName.ENERGY_SOURCE_1;
import static pwr.api.enums.FieldType.ENERGY_SOURCE;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnergySource1 extends Field
{
    private FieldType fieldType = ENERGY_SOURCE;
    private FieldName name = ENERGY_SOURCE_1;
    private int requiredEnergy = 3;

    public EnergySource1()
    {
        super();
        setFieldType(ENERGY_SOURCE);
        setName(ENERGY_SOURCE_1);
        setEnergy(3);
    }
}
