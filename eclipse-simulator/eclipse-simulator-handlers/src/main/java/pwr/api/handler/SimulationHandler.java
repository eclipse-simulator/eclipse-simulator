package pwr.api.handler;

import pwr.api.dto.FleetDTO;
import pwr.api.dto.ShipDTO;
import pwr.api.entity.ship.Ship;
import pwr.api.enums.FieldName;
import pwr.api.enums.ShipType;
import pwr.api.exception.BaseException;
import pwr.api.request.SimulationRequestData;
import pwr.api.response.SimulationResponseData;
import pwr.api.simulation.Simulation;
import pwr.api.simulation.SimulationResult;

import java.util.ArrayList;
import java.util.List;

import static pwr.api.enums.ExceptionType.ERROR;

public class SimulationHandler extends BaseHandler<SimulationRequestData, SimulationResponseData>
{
    @Override
    public SimulationResponseData processRequest(SimulationRequestData simulationRequestData)
    {

        validateRequest(simulationRequestData);

        List<Ship> firstPlayerShips = toShipList(simulationRequestData.getFirstPlayerFleet().getShips());
        List<Ship> secondPlayerShips = toShipList(simulationRequestData.getSecondPlayerFleet().getShips());

        validateShips(firstPlayerShips);
        validateShips(secondPlayerShips);

        Simulation simulation = new Simulation();

        SimulationResult simulationResult = simulation.simulateFight(firstPlayerShips, secondPlayerShips,
                simulationRequestData.getRepetitions());

        return new SimulationResponseData(simulationResult);
    }

    @Override
    protected void validateRequest(SimulationRequestData request)
    {
        FleetDTO firstPlayerFleet = request.getFirstPlayerFleet();
        FleetDTO secondPlayerFleet = request.getSecondPlayerFleet();

        if(request.getRepetitions() < 0)
            throw new BaseException(ERROR, "Repetitions number must be bigger then 0");

        if(firstPlayerFleet == null || secondPlayerFleet == null || firstPlayerFleet.getShips() == null ||
                secondPlayerFleet.getShips() == null || firstPlayerFleet.getShips().isEmpty() ||
                secondPlayerFleet.getShips().isEmpty())
            throw new BaseException(ERROR, "Fleet can't be empty");
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
