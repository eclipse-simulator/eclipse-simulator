package pwr.api.handler;

import pwr.api.dto.FleetDTO;
import pwr.api.dto.ShipDTO;
import pwr.api.dynamodb.DynamoDBClient;
import pwr.api.entity.ship.Ship;
import pwr.api.enums.FieldName;
import pwr.api.enums.ShipType;
import pwr.api.exception.ESApiException;
import pwr.api.request.SimulationRequestData;
import pwr.api.response.SimulationResponseData;
import pwr.api.simulation.Simulation;
import pwr.api.simulation.SimulationResult;

import java.util.ArrayList;
import java.util.List;

import static pwr.api.constants.Constants.errors.FLEETS_CANT_BE_EMPTY_ERROR_MESSAGE;
import static pwr.api.constants.Constants.errors.REPETITION_NUMBER_CANNOT_BE_ZERO_ERROR_MESSAGE;
import static pwr.api.enums.ErrorCode.BAD_REQUEST_ERROR;
import static pwr.api.enums.ExceptionType.ERROR;

public class SimulationHandler extends BaseHandler<SimulationRequestData, SimulationResponseData>
{
    private static final String ATTACKING_PLAYER_WIN_RATE = "Attacking player win rate: ";
    private static final String DEFENDING_PLAYER_WIN_RATE = "Defending player win rate: ";
    private static final String PERCENTAGE = "%";

    @Override
    public SimulationResponseData processRequest(SimulationRequestData simulationRequestData)
    {
        DynamoDBClient client = new DynamoDBClient();

        validateRequest(simulationRequestData);

        List<Ship> attackingPlayerShips = toShipList(simulationRequestData.getAttackingPlayerFleet().getShips());
        List<Ship> defendingPlayerShips = toShipList(simulationRequestData.getDefendingPlayerFleet().getShips());

        validateShips(attackingPlayerShips);
        validateShips(defendingPlayerShips);

        Simulation simulation = new Simulation();

        SimulationResult simulationResult = simulation.simulateFight(attackingPlayerShips, defendingPlayerShips,
                simulationRequestData.getRepetitions());

        System.out.println(ATTACKING_PLAYER_WIN_RATE + simulationResult.getAttackingPlayerWinRate() + PERCENTAGE);
        System.out.println(DEFENDING_PLAYER_WIN_RATE + simulationResult.getDefendingPlayerWinRate() + PERCENTAGE);

        client.updateItem(simulationRequestData.getAttackingPlayerFleet(),
                simulationRequestData.getDefendingPlayerFleet(),
                simulationResult);

        return new SimulationResponseData(simulationResult);
    }

    @Override
    protected void validateRequest(SimulationRequestData request)
    {
        FleetDTO attackingPlayerFleet = request.getAttackingPlayerFleet();
        FleetDTO defendingPlayerFleet = request.getDefendingPlayerFleet();

        if(request.getRepetitions() < 0)
            throw new ESApiException(ERROR, REPETITION_NUMBER_CANNOT_BE_ZERO_ERROR_MESSAGE, BAD_REQUEST_ERROR);

        if(attackingPlayerFleet == null || defendingPlayerFleet == null || attackingPlayerFleet.getShips() == null ||
                defendingPlayerFleet.getShips() == null || attackingPlayerFleet.getShips().isEmpty() ||
                defendingPlayerFleet.getShips().isEmpty())
            throw new ESApiException(ERROR, FLEETS_CANT_BE_EMPTY_ERROR_MESSAGE, BAD_REQUEST_ERROR);
    }

    private List<Ship> toShipList(List<ShipDTO> shipDTOList)
    {
        List<Ship> ships = new ArrayList<>();
        shipDTOList.forEach(shipDTO ->
        {
            Ship ship = ShipType.valueOf(shipDTO.getShipType()).createInstance();
            ship.setFields(new ArrayList<>());
            for(String field: shipDTO.getFields())
            {
                ship.getFields().add(FieldName.valueOf(field).createInstance());
            }
            ships.add(ship);
        });
        return ships;
    }

    private void validateShips(List<Ship> ships)
    {
        ships.forEach(Ship::validate);
    }
}
