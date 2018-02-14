package com.cenxui.shop.web.app.controller.point;

import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.point.PointRepository;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.web.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.web.app.controller.Controller;
import com.cenxui.shop.web.app.controller.util.Header;
import spark.Request;
import spark.Response;
import spark.Route;

public class PointController implements Controller {

    private static final PointRepository pointRepository =
            DynamoDBRepositoryService.getPointRepository(
                    AWSDynamoDBConfig.REGION,
                    AWSDynamoDBConfig.POINT_TABLE
            );

    public static final Route getPoint =  (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);
        return JsonUtil.mapToJsonIgnoreNull(pointRepository.getUserPoint(mail));
    };

}
