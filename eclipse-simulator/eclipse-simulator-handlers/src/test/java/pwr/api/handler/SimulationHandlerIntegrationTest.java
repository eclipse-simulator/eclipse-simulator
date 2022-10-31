package pwr.api.handler;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pwr.api.dto.FleetDTO;
import pwr.api.dto.ShipDTO;
import pwr.api.exception.BaseException;
import pwr.api.request.SimulationRequestData;
import pwr.api.response.BaseResponseData;
import pwr.api.response.SimulationResponseData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.api.constants.Constants.*;

public class SimulationHandlerIntegrationTest
{
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

        List<String> cruiserFields = new ArrayList<>();
        cruiserFields.add(ION_CANNON);
        cruiserFields.add(NUCLEAR_DRIVE);
        cruiserFields.add(GLUON_COMPUTER);
        cruiserFields.add(HULL);

        List<String> dreadnoughtFields = new ArrayList<>();
        dreadnoughtFields.add(ION_CANNON);
        dreadnoughtFields.add(ION_CANNON);
        dreadnoughtFields.add(GLUON_COMPUTER);
        dreadnoughtFields.add(NUCLEAR_DRIVE);
        dreadnoughtFields.add(HULL);
        dreadnoughtFields.add(HULL);

        List<ShipDTO> firstPlayerShips = new ArrayList<>();
        firstPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        firstPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        firstPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        firstPlayerShips.add(new ShipDTO(INTERCEPTOR, interceptorFields));
        firstPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        firstPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));
        firstPlayerShips.add(new ShipDTO(CRUISER, cruiserFields));

        List<ShipDTO> secondPlayerShips = new ArrayList<>();
        secondPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        secondPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        secondPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        secondPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));

        SimulationRequestData requestData = new SimulationRequestData(new FleetDTO(firstPlayerShips),
                new FleetDTO(secondPlayerShips),
                100);

        BaseResponseData<SimulationResponseData> response = handler.handleRequest(requestData, context);

        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void shouldCreateResponseWithAnError()
    {
        List<ShipDTO> firstPlayerShips = new ArrayList<>();
        List<ShipDTO> secondPlayerShips = new ArrayList<>();

        SimulationRequestData requestData = new SimulationRequestData(new FleetDTO(firstPlayerShips),
                new FleetDTO(secondPlayerShips),
                100);

        BaseResponseData<SimulationResponseData> response = handler.handleRequest(requestData, context);

        List<? super BaseException> errors = response.getErrors();
        assertNotEquals(0, errors.size());
        assertEquals("Fleet can't be empty", ((BaseException) errors.get(0)).getMessage());
    }
}
