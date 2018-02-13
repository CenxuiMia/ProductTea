package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.repositories.point.PointRepository;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamoDBPointBaseRepositoryTest {
    private PointRepository pointRepository =
            DynamoDBRepositoryService.getPointRepository(Config.REGION, Config.POINT_TABLE);


    @Test
    public void getAllUserPoint() throws Exception {

    }

    @Test
    public void getUserPoint() {
        System.out.println(pointRepository.getUserPoint("cenxuilin@gmail.com"));
    }

    @Test
    public void useUserPoint() throws Exception {
        System.out.println(pointRepository.useUserPoint("cenxuilin@gmail.com", 100L));

    }

    @Test
    public void addUserPoint() throws Exception {
        System.out.println(pointRepository.addUserPoint("cenxuilin@gmail.com", 100L));

    }

}