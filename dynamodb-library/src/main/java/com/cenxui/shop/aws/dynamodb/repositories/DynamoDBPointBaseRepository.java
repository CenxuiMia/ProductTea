package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.cenxui.shop.aws.dynamodb.exceptions.client.point.PointCannotUserException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.point.PointJsonMapException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.point.PointPrimaryKeyCannotEmptyException;
import com.cenxui.shop.repositories.point.Point;
import com.cenxui.shop.repositories.point.PointBaseRepository;
import com.cenxui.shop.util.JsonUtil;

import java.io.IOException;

class DynamoDBPointBaseRepository implements PointBaseRepository {

    private final Table pointTable;

    DynamoDBPointBaseRepository(Table pointTable) {
        this.pointTable = pointTable;
    }


    @Override
    public Point getAllUserPoint() {
        return null;
    }

    @Override
    public Point getUserPoint(String mail) {

        QuerySpec spec = new QuerySpec()
                .withHashKey(Point.MAIL, mail);

        ItemCollection<QueryOutcome> collection = pointTable.query(spec);

        return mapQueryOutcomeToPont(collection);
    }

    @Override
    public Point useUserPoint(String mail, Long value) {
        checkPrimaryKey(mail);

        UpdateItemSpec spec = new UpdateItemSpec()
                .withPrimaryKey(Point.MAIL, mail)
                .withConditionExpression(Point.VALUE + ">=:che")
                .withUpdateExpression("ADD " + Point.VALUE + " :val")
                .withValueMap(
                        new ValueMap()
                                .withLong(":val", -value)
                                .withLong(":che", value))
                .withReturnValues(ReturnValue.ALL_NEW);

        try {
            return getPoint(pointTable.updateItem(spec).getItem().toJSON());
        }catch (ConditionalCheckFailedException e) {
            throw new PointCannotUserException(value);
        }
    }

    @Override
    public Point addUserPoint(String mail, Long value) {
        checkPrimaryKey(mail);
        UpdateItemSpec spec = new UpdateItemSpec()
                .withPrimaryKey(Point.MAIL, mail)
                .withUpdateExpression("ADD " + Point.VALUE + " :val")
                .withValueMap(
                        new ValueMap()
                                .withLong(":val", value))
                .withReturnValues(ReturnValue.ALL_NEW);

        return getPoint(pointTable.updateItem(spec).getItem().toJSON());
    }

    private Point mapQueryOutcomeToPont(ItemCollection<QueryOutcome> collection) {
        if (collection.iterator().hasNext()) {
            return getPoint(collection.iterator().next().toJSON());
        }
        return null;
    }

    private Point getPoint(String pointJson) {
        try {
            return JsonUtil.mapToPoint(pointJson);
        } catch (IOException e) {
            throw new PointJsonMapException(pointJson);
        }
    }

    private void checkPrimaryKey(String key) {
        if (key == null || key.length() == 0) {
            throw new PointPrimaryKeyCannotEmptyException();

        }
    }
}
