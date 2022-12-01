package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.ENERGY_SOURCE_2;
import static pwr.api.enums.FieldType.ENERGY_SOURCE;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnergySource2 extends Field
{
    public EnergySource2()
    {
        super();
        setFieldType(ENERGY_SOURCE);
        setName(ENERGY_SOURCE_2);
        setEnergy(6);
    }
}
