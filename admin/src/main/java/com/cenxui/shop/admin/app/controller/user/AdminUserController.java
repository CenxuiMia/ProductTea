package com.cenxui.shop.admin.app.controller.user;

import com.cenxui.shop.admin.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.admin.app.controller.AdminCoreController;
import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.user.UserRepository;
import com.cenxui.shop.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class AdminUserController extends AdminCoreController {

    private static final UserRepository userRepository =
            DynamoDBRepositoryService.getUserRepository(
                    AWSDynamoDBConfig.REGION,
                    AWSDynamoDBConfig.USER_TABLE
            );

    public static final Route getAllUser = (Request request, Response response) -> {
        return JsonUtil.mapToJson(userRepository.getAllUsers());
    };

    public static final Route getUser = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route addUser = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route updateUser = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };

    public static final Route deleteUser = (Request request, Response response) -> {
        //todo
        throw new UnsupportedOperationException("not yet");
    };
}
