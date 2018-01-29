package com.cenxui.shop.web.app.controller.user;

import com.cenxui.shop.web.app.config.AWSDynamoDBConfig;
import com.cenxui.shop.repositories.user.User;
import com.cenxui.shop.web.app.controller.CoreController;
import com.cenxui.shop.util.JsonUtil;
import com.cenxui.shop.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.shop.repositories.user.UserRepository;
import com.cenxui.shop.web.app.controller.util.Header;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;

public class UserController extends CoreController {
    private static final UserRepository userRepository =
            DynamoDBRepositoryService.getUserRepository(
                    AWSDynamoDBConfig.REGION,
                    AWSDynamoDBConfig.USER_TABLE
            );


    public static final Route getUserProfile = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);

        User user = userRepository.getUserProfile(mail);

        return JsonUtil.mapToJsonIgnoreNull(user);
    };

    public static final Route updateUserProfile = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);

        User clientUser;

        try {
            clientUser = JsonUtil.mapToUser(request.body());
            return JsonUtil.mapToJsonIgnoreNull(
                    userRepository.updateUserProfile(
                            User.of(
                                    clientUser.getIsActive(),
                                    clientUser.getFirstName(),
                                    clientUser.getLastName(),
                                    mail,
                                    clientUser.getAddress(),
                                    clientUser.getPhone(),
                                    clientUser.getBirthday())));
        }catch (IOException e) {
            throw new UserControllerClientException("requst body error :" + request.body());
        }
    };
}
