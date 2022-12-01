package pwr.api.simulation;

import pwr.api.entity.roll.Roll;
import pwr.api.entity.ship.Ship;

import java.util.*;

public class Simulation
{
    public SimulationResult simulateFight(List<Ship> attackingPlayerFleet, List<Ship> defendingPlayerFleet, int repetitions)
    {
        float playerOnesWins = 0;

        List<Ship> attackingPlayerJunkyard = new ArrayList<>();
        List<Ship> defendingPlayerJunkyard = new ArrayList<>();

        for(int i = 0; i < repetitions; i++)
        {
            prepareFleets(attackingPlayerFleet, defendingPlayerFleet, attackingPlayerJunkyard, defendingPlayerJunkyard);

            assignMissileHits(attackingPlayerFleet, defendingPlayerFleet, attackingPlayerJunkyard, defendingPlayerJunkyard);

            int maxInitiative = findBiggestInitiative(attackingPlayerFleet, defendingPlayerFleet);
            int initiative = maxInitiative;

            while(!attackingPlayerFleet.isEmpty() && !defendingPlayerFleet.isEmpty())
            {
                if(initiative < 0)
                {
                    initiative = maxInitiative;
                }

                List<Roll> attackingPlayerCannonHits = new ArrayList<>();
                List<Roll> defendingPlayerCannonHits = new ArrayList<>();

                for(Ship ship: attackingPlayerFleet)
                {
                    if(ship.getInitiative() == initiative)
                    {
                        attackingPlayerCannonHits.addAll(ship.calculateCannonHits());
                    }
                }

                for(Ship ship: defendingPlayerFleet)
                {
                    if(ship.getInitiative() == initiative)
                    {
                        defendingPlayerCannonHits.addAll(ship.calculateCannonHits());
                    }
                }

                assignHits(attackingPlayerFleet, defendingPlayerCannonHits, attackingPlayerJunkyard);
                assignHits(defendingPlayerFleet, attackingPlayerCannonHits, defendingPlayerJunkyard);

                initiative--;
            }

            if(defendingPlayerFleet.isEmpty())
            {
                playerOnesWins++;
            }
        }

        float attackingPlayerWinRate = (playerOnesWins / repetitions) * 100;
        float defendingPlayerWinRate = ((repetitions - playerOnesWins) / repetitions) * 100;

        return new SimulationResult(attackingPlayerWinRate, defendingPlayerWinRate);
    }

    private void restartFleets(List<Ship> attackingPlayerFleet, List<Ship> defendingPlayerFleet,
                               List<Ship> attackingPlayerJunkyard, List<Ship> defendingPlayerJunkyard)
    {
        attackingPlayerFleet.addAll(attackingPlayerJunkyard);
        defendingPlayerFleet.addAll(defendingPlayerJunkyard);

        attackingPlayerJunkyard.clear();
        defendingPlayerJunkyard.clear();

        attackingPlayerFleet.forEach(ship -> ship.setDead(false));
        defendingPlayerFleet.forEach(ship -> ship.setDead(false));
    }

    private void prepareFleets(List<Ship> attackingPlayerFleet, List<Ship> defendingPlayerFleet,
                               List<Ship> attackingPlayerJunkyard, List<Ship> defendingPlayerJunkyard)

    {
        restartFleets(attackingPlayerFleet, defendingPlayerFleet, attackingPlayerJunkyard, defendingPlayerJunkyard);

        attackingPlayerFleet.sort(Comparator.comparing(Ship::getShipType));
        defendingPlayerFleet.sort(Comparator.comparing(Ship::getShipType));

        attackingPlayerFleet.forEach(Ship::calculateStats);
        defendingPlayerFleet.forEach(Ship::calculateStats);
    }

    private void assignMissileHits(List<Ship> attackingPlayerFleet, List<Ship> defendingPlayerFleet,
                                   List<Ship> attackingPlayerJunkyard, List<Ship> defendingPlayerJunkyard)
    {
        List<Roll> attackingPlayerMissileHits = new ArrayList<>();
        List<Roll> defendingPlayerMissileHits = new ArrayList<>();

        for(Ship ship: attackingPlayerFleet)
        {
            attackingPlayerMissileHits.addAll(ship.calculateMissileHits());
        }

        for(Ship ship: defendingPlayerFleet)
        {
            defendingPlayerMissileHits.addAll(ship.calculateMissileHits());
        }

        assignHits(attackingPlayerFleet, defendingPlayerMissileHits, attackingPlayerJunkyard);
        assignHits(defendingPlayerFleet, attackingPlayerMissileHits, defendingPlayerJunkyard);
    }

    private int findBiggestInitiative(List<Ship> attackingPlayerFleet, List<Ship> defendingPlayerFleet)
    {
        int initiative = 0;

        for (Ship ship : attackingPlayerFleet) {
            if (ship.getInitiative() > initiative) {
                initiative = ship.getInitiative();
            }
        }

        for (Ship ship : defendingPlayerFleet) {
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
}
