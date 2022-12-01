package pwr.api.entity.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static pwr.api.enums.FieldName.ENERGY_SOURCE_3;
import static pwr.api.enums.FieldType.ENERGY_SOURCE;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnergySource3 extends Field
{
    public EnergySource3()
    {
        super();
        setFieldType(ENERGY_SOURCE);
        setName(ENERGY_SOURCE_3);
        setEnergy(9);
    }
}
