package com.cenxui.tea.app.services.admin.user;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.user.UserRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Param;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class AdminUserController extends CoreController{

    private static final UserRepository userRepository =
            DynamoDBRepositoryService.getUserRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.USER_TABLE
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
