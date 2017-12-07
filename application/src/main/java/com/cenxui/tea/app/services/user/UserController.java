package com.cenxui.tea.app.services.user;

import com.cenxui.tea.app.aws.dynamodb.repositories.DynamoDBRepositoryService;
import com.cenxui.tea.app.config.DynamoDBConfig;
import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.repositories.user.UserRepository;
import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.util.Header;
import com.cenxui.tea.app.util.JsonUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController extends CoreController {
    private static final UserRepository userRepository =
            DynamoDBRepositoryService.getUserRepository(
                    DynamoDBConfig.REGION,
                    DynamoDBConfig.USER_TABLE
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
        }catch (Exception e) {
            throw new UserControllerClientException("requst body error :" + request.body());
        }
        return JsonUtil.mapToJsonIgnoreNull(
                userRepository.updateUserProfile(
                        User.of(
                                clientUser.getIsActive(),
                                clientUser.getFirstName(),
                                clientUser.getLastName(),
                                mail,
                                clientUser.getAddress(),
                                clientUser.getPhone())));
    };
}
