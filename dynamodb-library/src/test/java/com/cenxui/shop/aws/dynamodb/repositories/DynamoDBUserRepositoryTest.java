package com.cenxui.shop.aws.dynamodb.repositories;

import com.cenxui.shop.repositories.user.User;
import com.cenxui.shop.repositories.user.UserRepository;
import com.cenxui.shop.util.JsonUtil;
import org.junit.Test;

public class DynamoDBUserRepositoryTest {

    private UserRepository userRepository =
            DynamoDBRepositoryService.getUserRepository(
                    Config.REGION,
                    Config.USER_TABLE
            );

    @Test
    public void updateUserProfile() throws Exception {
//        User user = User.of(
//                true,
//                "Lin",
//                "Cenxui",
//                "cenxui@gmail.com",
//                "A",
//                "0928554033"
//        );
//
//        System.out.println(JsonUtil.mapToJson(userRepository.updateUserProfile(user)));
    }

    @Test
    public void getUserProfile() throws Exception {

        System.out.println(JsonUtil.mapToJson(userRepository.getUserProfile("cenxui@gmail.com")));
    }

}