package pwr.api.dynamodb;

import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import org.junit.jupiter.api.Test;
import pwr.api.dto.FleetDTO;
import pwr.api.dto.ShipDTO;
import pwr.api.simulation.SimulationResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pwr.api.constants.Constants.dynamo.*;
import static pwr.api.constants.Constants.fields.*;
import static pwr.api.constants.Constants.ships.*;
import static pwr.api.dynamodb.DynamoDBClient.sanitizeString;

public class DynamoDBClientIntegrationTest
{
    private static final float ATTACKING_PLAYER_WIN_RATE = 20;
    private static final float DEFENDING_PLAYER_WIN_RATE = 80;
    private static final int HTTP_OK_STATUS = 200;

    @Test
    public void shouldPutItemAndGetItemSuccessfully()
    {
        FleetDTO attackingPlayerFleet = getAttackingFleet();
        FleetDTO defendingPlayerFleet = getDefendingFleet();

        DynamoDBClient client = new DynamoDBClient();

        SimulationResult simulationResult = new SimulationResult(ATTACKING_PLAYER_WIN_RATE, DEFENDING_PLAYER_WIN_RATE);

        PutItemResult putResult = client.updateItem(attackingPlayerFleet, defendingPlayerFleet, simulationResult);
        assertEquals(HTTP_OK_STATUS, putResult.getSdkHttpMetadata().getHttpStatusCode());

        GetItemResult result = client.getItem(attackingPlayerFleet, defendingPlayerFleet);
        assertEquals(HTTP_OK_STATUS, result.getSdkHttpMetadata().getHttpStatusCode());

        assertEquals(attackingPlayerFleet.hashCode(),
                Integer.valueOf(sanitizeString(result.getItem().get(ATTACKING_FLEET_HASH).toString())));
        assertEquals(defendingPlayerFleet.hashCode(),
                Integer.valueOf(sanitizeString(result.getItem().get(DEFENDING_FLEET_HASH).toString())));
        assertEquals(ATTACKING_PLAYER_WIN_RATE,
                Float.valueOf(sanitizeString(result.getItem().get(ATTACKING_FLEET_WIN_RATE).toString())));
        assertEquals(DEFENDING_PLAYER_WIN_RATE,
                Float.valueOf(sanitizeString(result.getItem().get(DEFENDING_FLEET_WIN_RATE).toString())));
    }

    private FleetDTO getAttackingFleet()
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

    private FleetDTO getDefendingFleet()
    {
        List<String> dreadnoughtFields = new ArrayList<>();
        dreadnoughtFields.add(ION_CANNON);
        dreadnoughtFields.add(ION_CANNON);
        dreadnoughtFields.add(GLUON_COMPUTER);
        dreadnoughtFields.add(NUCLEAR_DRIVE);
        dreadnoughtFields.add(HULL);
        dreadnoughtFields.add(HULL);
        dreadnoughtFields.add(ENERGY_SOURCE_2);

        List<ShipDTO> defendingPlayerShips = new ArrayList<>();

        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));
        defendingPlayerShips.add(new ShipDTO(DREADNOUGHT, dreadnoughtFields));

        return new FleetDTO(defendingPlayerShips);
    }

    @Test
    public void getItemShouldReturnNull()
    {
        DynamoDBClient client = new DynamoDBClient();
        GetItemResult result = client.getItem(getAttackingFleet(), getAttackingFleet());
        assertNull(result.getItem());
    }
}
