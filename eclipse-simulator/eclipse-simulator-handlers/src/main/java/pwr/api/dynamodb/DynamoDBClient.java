package pwr.api.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import pwr.api.dto.FleetDTO;

import java.util.HashMap;
import java.util.Map;

import static pwr.api.constants.Constants.dynamo.*;

public class DynamoDBClient {
    final AmazonDynamoDB dynamoClient;

    public DynamoDBClient() {
        dynamoClient = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public PutItemResult updateItem(FleetDTO attackingPlayerFleet, FleetDTO defendingPlayerFleet, float attackingPlayerWinRate,
                      float defendingPlayerWinRate)
    {
        GetItemResult item = getItem(attackingPlayerFleet, defendingPlayerFleet);
        if(item.getItem().isEmpty())
        {
            return putItem(attackingPlayerFleet, defendingPlayerFleet, attackingPlayerWinRate, defendingPlayerWinRate, 1);
        } else
        {
            Map<String, AttributeValue> updatedItem = item.getItem();
            int counter = Integer.parseInt(item.getItem().get(COUNTER).toString());
            float oldAttackingPlayerWinRate = Float.parseFloat(updatedItem.get(ATTACKING_FLEET_WIN_RATE).toString());
            oldAttackingPlayerWinRate *= counter;
            float newAttackingPlayerWinRate = (oldAttackingPlayerWinRate + attackingPlayerWinRate) / (counter + 1);
            float oldDefendingPlayerWinRate = Float.parseFloat(updatedItem.get(DEFENDING_FLEET_WIN_RATE).toString());
            oldDefendingPlayerWinRate *= counter;
            float newDefendingPlayerWinRate = (oldDefendingPlayerWinRate + defendingPlayerWinRate) / (counter + 1);
            updatedItem.put(ATTACKING_FLEET_HASH, new AttributeValue(String.valueOf(attackingPlayerFleet.hashCode())));
            updatedItem.put(DEFENDING_FLEET_HASH, new AttributeValue(String.valueOf(defendingPlayerFleet.hashCode())));
            updatedItem.put(ATTACKING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(newAttackingPlayerWinRate)));
            updatedItem.put(DEFENDING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(newDefendingPlayerWinRate)));
            updatedItem.put(COUNTER, new AttributeValue(String.valueOf(counter + 1)));
            return dynamoClient.putItem(DYNAMO_TABLE_NAME, updatedItem);
        }
    }

    public PutItemResult putItem(FleetDTO attackingPlayerFleet, FleetDTO defendingPlayerFleet, float attackingPlayerWinRate,
                                 float defendingPlayerWinRate, int counter)
    {
        HashMap<String, AttributeValue> item = new HashMap<>();
        item.put(ATTACKING_FLEET_HASH, new AttributeValue(String.valueOf(attackingPlayerFleet.hashCode())));
        item.put(DEFENDING_FLEET_HASH, new AttributeValue(String.valueOf(defendingPlayerFleet.hashCode())));
        item.put(ATTACKING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(attackingPlayerWinRate)));
        item.put(DEFENDING_FLEET_WIN_RATE, new AttributeValue(String.valueOf(defendingPlayerWinRate)));
        item.put(COUNTER, new AttributeValue(String.valueOf(counter)));
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
