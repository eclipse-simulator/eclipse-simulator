package pwr.api.entity.ship;

import lombok.Data;
import pwr.api.entity.roll.Roll;
import pwr.api.entity.upgrade.Field;
import pwr.api.enums.FieldType;
import pwr.api.enums.ShipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pwr.api.constants.Constants.MAX_ROLL;
import static pwr.api.enums.FieldType.CANNON;
import static pwr.api.enums.FieldType.MISSILE;

@Data
public abstract class Ship
{
    private ShipType shipType;
    private int initiative;
    private int hp;
    private int hitChanceBuff;
    private int hitChanceDeBuff;
    private boolean hasSplitter;
    private boolean dead;
    private List<Field> fields;
    private Random random;

    public Ship()
    {
        this.initiative = 0;
        this.hp = 1;
        this.hitChanceBuff = 0;
        this.hitChanceDeBuff = 0;
        this.hasSplitter = false;
        this.dead = false;
        this.fields = new ArrayList<>();
        this.random = new Random();
    }

    public void calculateStats()
    {
        calculateInitiative();
        calculateHp();
        calculateHitChanceBuff();
        calculateHitChanceDeBuff();
    }

    public List<Roll> calculateMissileHits()
    {
        List<Roll> rolls = new ArrayList<>();
        getFields().stream()
                .filter(field -> field.getFieldType().equals(MISSILE))
                .forEach(field ->
                {
                    int roll = random.nextInt(MAX_ROLL);
                    roll += getHitChanceDeBuff();
                    if(roll >= MAX_ROLL - 1)
                    {
                        rolls.add(new Roll(roll, field.getDamage(),false));
                    }
                });
        if(isHasSplitter())
        {
            return splitRolls(rolls);
        }
        return rolls;
    }

    public List<Roll> calculateCannonHits()
    {
        List<Roll> rolls = new ArrayList<>();
        getFields().stream()
                .filter(field -> field.getFieldType().equals(CANNON))
                .forEach(field ->
                {
                    int roll = random.nextInt(MAX_ROLL);
                    roll += getHitChanceBuff();
                    if(roll >= MAX_ROLL - 1)
                    {
                        rolls.add(new Roll(roll, field.getDamage(),false));
                    }
                });
        if(isHasSplitter())
        {
            return splitRolls(rolls);
        }
        return rolls;
    }

    public void assignHits(List<Roll> rolls)
    {
        for(Roll roll: rolls)
        {
            if(!isDead())
            {
                roll.setShotFired(true);
                if(roll.getValue() >= MAX_ROLL - 1 + getHitChanceDeBuff())
                {
                    setHp(getHp() - roll.getDamage());
                    if(getHp() < 0)
                    {
                        setDead(true);
                    }
                }
            }
        }
    }

    public abstract void validate();

    private void calculateInitiative()
    {
        initiative = 0;
        getFields().forEach(field -> initiative += field.getInitiative());
    }

    private void calculateHp()
    {
        hp = 1;
        getFields().stream()
                .filter(field -> field.getFieldType().equals(FieldType.HULL))
                .forEach(field -> hp += field.getHp());
    }

    private void calculateHitChanceBuff()
    {
        hitChanceBuff = 0;
        getFields().stream()
                .filter(field -> field.getFieldType().equals(FieldType.COMPUTER))
                .forEach(field -> hitChanceBuff += field.getHitChanceBuff());
    }

    private void calculateHitChanceDeBuff()
    {
        hitChanceDeBuff = 0;
        getFields().stream()
                .filter(field -> field.getFieldType().equals(FieldType.SHIELD))
                .forEach(field -> hitChanceDeBuff += field.getHitChanceDeBuff());
    }

    private List<Roll> splitRolls(List<Roll> rolls)
    {
        List<Roll> splittedHits = new ArrayList<>();
        for(Roll roll: rolls)
        {
            for(int i = 0; i < roll.getDamage(); i++)
            {
                splittedHits.add(new Roll(roll.getValue(), 1, false));
            }
        }
        return splittedHits;
    }
}
