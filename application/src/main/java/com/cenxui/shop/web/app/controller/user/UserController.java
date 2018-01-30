package com.cenxui.shop.web.app.controller.user;

import com.cenxui.shop.repositories.user.attribute.UserAttributeFilter;
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

        if (mail == null) throw new UserControllerServerException("header mail cannot be null.");

        if (mail.length() == 0) throw new UserControllerServerException("header mail cannot be empty.");

        User user = mapRequestBodyToUser(request.body());

        checkUser(user);

        return JsonUtil.mapToJsonIgnoreNull(
                userRepository.updateUserProfile(
                        User.of(
                                user.getIsActive(),
                                user.getFirstName(),
                                user.getLastName(),
                                mail,
                                user.getAddress(),
                                user.getPhone(),
                                user.getBirthday())));
    };

    private static User mapRequestBodyToUser(String body) {
        try {
            return JsonUtil.mapToUser(body);
        } catch (IOException e) {
            throw new UserControllerClientException("requst body is not allowed");
        }
    }

    private static void checkUser(User user) {
       if (!UserAttributeFilter.checkFirstName(user.getFirstName())) {
           throw new UserControllerClientException("firstName attribute is not allowed");
       }

       if (!UserAttributeFilter.checkLastName(user.getLastName())) {
           throw new UserControllerClientException("lastName attribute is not allowed");
       }

       if (!UserAttributeFilter.checkBirthday(user.getBirthday())) {
           throw new UserControllerClientException("birthday attribute is not allowed");
       }

       if (!UserAttributeFilter.checkAddress(user.getAddress())) {
           throw new UserControllerClientException("address attribute is not allowed");
       }

       if (!UserAttributeFilter.checkPhone(user.getPhone())) {
           throw new UserControllerClientException("phone attribute is not allowed");
       }
    }
}
