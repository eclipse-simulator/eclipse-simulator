package pwr.api.simulation;

import pwr.api.entity.roll.Roll;
import pwr.api.entity.ship.Ship;

import java.util.*;

public class Simulation
{
    public SimulationResult simulateFight(List<Ship> firstPlayerFleet, List<Ship> secondPlayerFleet, int repetitions)
    {
        float playerOnesWins = 0;

        List<Ship> firstPlayerJunkyard = new ArrayList<>();
        List<Ship> secondPlayerJunkyard = new ArrayList<>();

        for(int i = 0; i < repetitions; i++)
        {
            prepareFleets(firstPlayerFleet, secondPlayerFleet, firstPlayerJunkyard, secondPlayerJunkyard);

            assignMissileHits(firstPlayerFleet, secondPlayerFleet, firstPlayerJunkyard, secondPlayerJunkyard);

            int maxInitiative = findBiggestInitiative(firstPlayerFleet, secondPlayerFleet);
            int initiative = maxInitiative;

            while(!firstPlayerFleet.isEmpty() && !secondPlayerFleet.isEmpty())
            {
                if(initiative < 0)
                {
                    initiative = maxInitiative;
                }

                List<Roll> firstPlayerCannonHits = new ArrayList<>();
                List<Roll> secondPlayerCannonHits = new ArrayList<>();

                for(Ship ship: firstPlayerFleet)
                {
                    if(ship.getInitiative() == initiative)
                    {
                        firstPlayerCannonHits.addAll(ship.calculateCannonHits());
                    }
                }

                for(Ship ship: secondPlayerFleet)
                {
                    if(ship.getInitiative() == initiative)
                    {
                        secondPlayerCannonHits.addAll(ship.calculateCannonHits());
                    }
                }

                assignHits(firstPlayerFleet, secondPlayerCannonHits, firstPlayerJunkyard);
                assignHits(secondPlayerFleet, firstPlayerCannonHits, secondPlayerJunkyard);

                initiative--;
            }

            if(secondPlayerFleet.isEmpty())
            {
                playerOnesWins++;
            }
        }

        float firstPlayerWinRate = (playerOnesWins / repetitions) * 100;
        float secondPlayerWinRate = ((repetitions - playerOnesWins) / repetitions) * 100;

        return new SimulationResult(firstPlayerWinRate, secondPlayerWinRate);
    }

    private void restartFleets(List<Ship> firstPlayerFleet, List<Ship> secondPlayerFleet,
                               List<Ship> firstPlayerJunkyard, List<Ship> secondPlayerJunkyard)
    {
        firstPlayerFleet.addAll(firstPlayerJunkyard);
        secondPlayerFleet.addAll(secondPlayerJunkyard);

        firstPlayerJunkyard.clear();
        secondPlayerJunkyard.clear();

        firstPlayerFleet.forEach(ship -> ship.setDead(false));
        secondPlayerFleet.forEach(ship -> ship.setDead(false));
    }

    private void prepareFleets(List<Ship> firstPlayerFleet, List<Ship> secondPlayerFleet,
                               List<Ship> firstPlayerJunkyard, List<Ship> secondPlayerJunkyard)
    {
        restartFleets(firstPlayerFleet, secondPlayerFleet, firstPlayerJunkyard, secondPlayerJunkyard);

        firstPlayerFleet.sort(Comparator.comparing(Ship::getShipType));
        secondPlayerFleet.sort(Comparator.comparing(Ship::getShipType));

        firstPlayerFleet.forEach(Ship::calculateStats);
        secondPlayerFleet.forEach(Ship::calculateStats);
    }

    private void assignMissileHits(List<Ship> firstPlayerFleet, List<Ship> secondPlayerFleet,
                                   List<Ship> firstPlayerJunkyard, List<Ship> secondPlayerJunkyard)
    {
        List<Roll> firstPlayerMissileHits = new ArrayList<>();
        List<Roll> secondPlayerMissileHits = new ArrayList<>();

        for(Ship ship: firstPlayerFleet)
        {
            firstPlayerMissileHits.addAll(ship.calculateMissileHits());
        }

        for(Ship ship: secondPlayerFleet)
        {
            secondPlayerMissileHits.addAll(ship.calculateMissileHits());
        }

        assignHits(firstPlayerFleet, secondPlayerMissileHits, firstPlayerJunkyard);
        assignHits(secondPlayerFleet, firstPlayerMissileHits, secondPlayerJunkyard);
    }

    private int findBiggestInitiative(List<Ship> firstPlayerFleet, List<Ship> secondPlayerFleet)
    {
        int initiative = 0;

        for (Ship ship : firstPlayerFleet) {
            if (ship.getInitiative() > initiative) {
                initiative = ship.getInitiative();
            }
        }

        for (Ship ship : secondPlayerFleet) {
            if (ship.getInitiative() > initiative) {
                initiative = ship.getInitiative();
            }
        }

        return initiative;
    }

    private void assignHits(List<Ship> fleet, List<Roll> rolls, List<Ship> junkyard)
    {
        assignHitsWeakestShipsFirst(fleet, rolls, junkyard);
    }

    private void assignHitsWeakestShipsFirst(List<Ship> fleet, List<Roll> rolls, List<Ship> junkyard)
    {
        while(!rolls.isEmpty() && !fleet.isEmpty())
        {
            fleet.get(0).assignHits(rolls);

            rolls.removeIf(Roll::isShotFired);
            fleet.stream().filter(Ship::isDead).forEach(junkyard::add);
            fleet.removeIf(Ship::isDead);
        }
    }

    private void assignHitsStrongestShipsFirst()
    {

    }
}
