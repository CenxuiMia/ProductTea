package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.cenxui.shop.repositories.point.Point;
import com.cenxui.shop.repositories.point.PointRepository;

class DynamoDBPointRepository implements PointRepository {

    private final DynamoDBPointBaseRepository pointBaseRepository;

    DynamoDBPointRepository(Table pointTable) {
        this.pointBaseRepository = new DynamoDBPointBaseRepository(pointTable);
    }

    @Override
    public Point getAllUserPoint() {
        return pointBaseRepository.getAllUserPoint();
    }

    @Override
    public Point getUserPoint(String mail) {
        return pointBaseRepository.getUserPoint(mail);
    }

    @Override
    public Point useUserPoint(String mail, Long value) {
        return pointBaseRepository.useUserPoint(mail, value);
    }

    @Override
    public Point addUserPoint(String mail, Long value) {
        return pointBaseRepository.addUserPoint(mail, value);
    }
}
