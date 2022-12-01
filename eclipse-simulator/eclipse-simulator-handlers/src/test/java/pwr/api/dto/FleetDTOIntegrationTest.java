package pwr.api.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pwr.api.constants.Constants.fields.*;
import static pwr.api.constants.Constants.ships.CRUISER;
import static pwr.api.constants.Constants.ships.INTERCEPTOR;

public class FleetDTOIntegrationTest
{
    @Test
    public void allHashCodesShouldBeEqual()
    {
        assertEquals(getFleet1().hashCode(), getFleet2().hashCode());
        assertEquals(getFleet1().hashCode(), getFleet3().hashCode());
    }

    @Test
    public void allHashCodesShouldNotBeEqual()
    {
        assertNotEquals(getFleet1().hashCode(), getFleet4().hashCode());
        assertNotEquals(getFleet1().hashCode(), getFleet5().hashCode());
        assertNotEquals(getFleet4().hashCode(), getFleet5().hashCode());
    }

    public FleetDTO getFleet1()
    {
        List<String> interceptorFields = new ArrayList<>();
        interceptorFields.add(ION_CANNON);
        interceptorFields.add(NUCLEAR_DRIVE);
        interceptorFields.add(ENERGY_SOURCE_1);

        List<String> cruiserFields = new ArrayList<>();
        cruiserFields.add(ION_CANNON);
        cruiserFields.add(NUCLEAR_DRIVE);
        cruiserFields.add(GLUON_COMPUTER);
        cruiserFields.add(HULL);
        cruiserFields.add(ENERGY_SOURCE_2);

        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        return new FleetDTO(attackingPlayerShips);
    }

    // fleet 1 with cruiser fields in different order
    public FleetDTO getFleet2()
    {
        List<String> interceptorFields = new ArrayList<>();
        interceptorFields.add(ION_CANNON);
        interceptorFields.add(NUCLEAR_DRIVE);
        interceptorFields.add(ENERGY_SOURCE_1);

        List<String> cruiserFields = new ArrayList<>();
        cruiserFields.add(ION_CANNON);
        cruiserFields.add(NUCLEAR_DRIVE);
        cruiserFields.add(GLUON_COMPUTER);
        cruiserFields.add(ENERGY_SOURCE_2);
        cruiserFields.add(HULL);

        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        return new FleetDTO(attackingPlayerShips);
    }

    // fleet 1 with ships in different order
    public FleetDTO getFleet3()
    {
        List<String> interceptorFields = new ArrayList<>();
        interceptorFields.add(ION_CANNON);
        interceptorFields.add(NUCLEAR_DRIVE);
        interceptorFields.add(ENERGY_SOURCE_1);

        List<String> cruiserFields = new ArrayList<>();
        cruiserFields.add(ION_CANNON);
        cruiserFields.add(NUCLEAR_DRIVE);
        cruiserFields.add(GLUON_COMPUTER);
        cruiserFields.add(HULL);
        cruiserFields.add(ENERGY_SOURCE_2);

        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        return new FleetDTO(attackingPlayerShips);
    }

    public FleetDTO getFleet4()
    {
        List<String> interceptorFields = new ArrayList<>();
        interceptorFields.add(ION_CANNON);
        interceptorFields.add(NUCLEAR_DRIVE);
        interceptorFields.add(ENERGY_SOURCE_1);

        List<String> cruiserFields = new ArrayList<>();
        cruiserFields.add(ION_CANNON);
        cruiserFields.add(NUCLEAR_DRIVE);
        cruiserFields.add(GLUON_COMPUTER);
        cruiserFields.add(HULL);
        cruiserFields.add(ENERGY_SOURCE_3);

        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        return new FleetDTO(attackingPlayerShips);
    }

    public FleetDTO getFleet5()
    {
        List<String> interceptorFields = new ArrayList<>();
        interceptorFields.add(ION_CANNON);
        interceptorFields.add(NUCLEAR_DRIVE);
        interceptorFields.add(ENERGY_SOURCE_1);

        List<String> cruiserFields = new ArrayList<>();
        cruiserFields.add(ION_CANNON);
        cruiserFields.add(NUCLEAR_DRIVE);
        cruiserFields.add(GLUON_COMPUTER);
        cruiserFields.add(HULL);
        cruiserFields.add(ENERGY_SOURCE_1);

        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        return new FleetDTO(attackingPlayerShips);
    }
}
