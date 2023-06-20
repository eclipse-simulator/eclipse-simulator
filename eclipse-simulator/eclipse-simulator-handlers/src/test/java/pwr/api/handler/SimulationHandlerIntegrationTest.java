package pwr.api.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pwr.api.dto.FleetDTO;
import pwr.api.dto.ShipDTO;
import pwr.api.exception.ESApiException;
import pwr.api.handler.helpers.LambdaMockContext;
import pwr.api.request.SimulationRequestData;
import pwr.api.response.BaseResponseData;
import pwr.api.response.SimulationResponseData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.api.constants.Constants.fields.*;
import static pwr.api.constants.Constants.ships.*;

public class SimulationHandlerIntegrationTest
{
    private static final String FLEET_CANT_BE_EMPTY = "Fleet can't be empty";
    private static Context context;
    private static SimulationHandler handler;

    @BeforeAll
    static void loadConfigurationFromEnvironment()
    {
        context = new LambdaMockContext();
        handler = new SimulationHandler();
    }

    @Test
    public void shouldSimulateFightSuccessfully()
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

        List<String> dreadnoughtFields = new ArrayList<>();
        dreadnoughtFields.add(ION_CANNON);
        dreadnoughtFields.add(ION_CANNON);
        dreadnoughtFields.add(GLUON_COMPUTER);
        dreadnoughtFields.add(NUCLEAR_DRIVE);
        dreadnoughtFields.add(HULL);
        dreadnoughtFields.add(HULL);
        dreadnoughtFields.add(ENERGY_SOURCE_2);

        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        attackingPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        List<ShipDTO> defendingPlayerShips = new ArrayList<>();
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));

        SimulationRequestData requestData = new SimulationRequestData(new FleetDTO(attackingPlayerShips),
                new FleetDTO(defendingPlayerShips),
                100);

        BaseResponseData<SimulationResponseData> response = handler.handleRequest(requestData, context);

        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void shouldCreateResponseWithAnError()
    {
        List<ShipDTO> attackingPlayerShips = new ArrayList<>();
        List<ShipDTO> defendingPlayerShips = new ArrayList<>();

        SimulationRequestData requestData = new SimulationRequestData(new FleetDTO(attackingPlayerShips),
                new FleetDTO(defendingPlayerShips),
                1000);

        BaseResponseData<SimulationResponseData> response = handler.handleRequest(requestData, context);

        List<? super ESApiException> errors = response.getErrors();
        assertNotEquals(0, errors.size());
        assertEquals(FLEET_CANT_BE_EMPTY, ((ESApiException) errors.get(0)).getMessage());
    }
}
