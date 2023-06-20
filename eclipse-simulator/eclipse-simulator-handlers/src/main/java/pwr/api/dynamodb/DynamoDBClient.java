package pwr.api.dynamodb;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import pwr.api.dto.FleetDTO;
import pwr.api.simulation.SimulationResult;

import java.util.HashMap;
import java.util.Map;

import static pwr.api.constants.Constants.dynamo.*;

public class DynamoDBClient {
    final AmazonDynamoDB dynamoClient;

    public DynamoDBClient() {
        dynamoClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_2)
                .build();
    }

    public PutItemResult updateItem(FleetDTO attackingPlayerFleet, FleetDTO defendingPlayerFleet,
                                    SimulationResult simulationResult)
    {
        GetItemResult item = getItem(attackingPlayerFleet, defendingPlayerFleet);
        float attackingPlayerWinRate = simulationResult.getAttackingPlayerWinRate();
        float defendingPlayerWinRate = simulationResult.getDefendingPlayerWinRate();
        if(item == null || item.getItem() == null)
        {
            return putItem(attackingPlayerFleet, defendingPlayerFleet, attackingPlayerWinRate, defendingPlayerWinRate);
        } else
        {
            Map<String, AttributeValue> updatedItem = item.getItem();
            int counter = Integer.parseInt(sanitizeString(item.getItem().get(COUNTER).toString()));
            float oldAttackingPlayerWinRate = Float.parseFloat(sanitizeString(updatedItem.get(ATTACKING_FLEET_WIN_RATE).toString()));
            oldAttackingPlayerWinRate *= counter;
            float newAttackingPlayerWinRate = (oldAttackingPlayerWinRate + attackingPlayerWinRate) / (counter + 1);
            float oldDefendingPlayerWinRate = Float.parseFloat(sanitizeString(updatedItem.get(DEFENDING_FLEET_WIN_RATE).toString()));
            oldDefendingPlayerWinRate *= counter;
            float newDefendingPlayerWinRate = (oldDefendingPlayerWinRate + defendingPlayerWinRate) / (counter + 1);
            updatedItem.put(ATTACKING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(newAttackingPlayerWinRate)));
            updatedItem.put(DEFENDING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(newDefendingPlayerWinRate)));
            updatedItem.put(COUNTER, new AttributeValue(String.valueOf(counter + 1)));
            simulationResult.setAttackingPlayerWinRate(newAttackingPlayerWinRate);
            simulationResult.setDefendingPlayerWinRate(newDefendingPlayerWinRate);
            return dynamoClient.putItem(DYNAMO_TABLE_NAME, updatedItem);
        }
    }

    private PutItemResult putItem(FleetDTO attackingPlayerFleet, FleetDTO defendingPlayerFleet, float attackingPlayerWinRate,
                                 float defendingPlayerWinRate)
    {
        HashMap<String, AttributeValue> item = new HashMap<>();
        item.put(ATTACKING_FLEET_HASH, new AttributeValue(String.valueOf(attackingPlayerFleet.hashCode())));
        item.put(DEFENDING_FLEET_HASH, new AttributeValue(String.valueOf(defendingPlayerFleet.hashCode())));
        item.put(ATTACKING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(attackingPlayerWinRate)));
        item.put(DEFENDING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(defendingPlayerWinRate)));
        item.put(COUNTER, new AttributeValue(String.valueOf(1)));
        return dynamoClient.putItem(DYNAMO_TABLE_NAME, item);
    }

    public GetItemResult getItem(FleetDTO attackingPlayerFleet, FleetDTO defendingPlayerFleet)
    {
        HashMap<String, AttributeValue> item = new HashMap<>();
        item.put(ATTACKING_FLEET_HASH, new AttributeValue(String.valueOf(attackingPlayerFleet.hashCode())));
        item.put(DEFENDING_FLEET_HASH, new AttributeValue(String.valueOf(defendingPlayerFleet.hashCode())));
        return dynamoClient.getItem(DYNAMO_TABLE_NAME, item);
    }

    public static String sanitizeString(String valueFromDynamo)
    {
        valueFromDynamo = valueFromDynamo
                .replace("\"", "")
                .replace("{", "")
                .replace("S", "")
                .replace(":", "")
                .replace(" ", "")
                .replace(",", "")
                .replace("}", "");

        return valueFromDynamo;
    }
}
