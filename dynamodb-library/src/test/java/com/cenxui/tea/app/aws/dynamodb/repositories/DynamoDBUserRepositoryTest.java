package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.repositories.user.UserRepository;
import com.cenxui.tea.app.util.JsonUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class DynamoDBUserRepositoryTest {

    private UserRepository userRepository =
            DynamoDBRepositoryService.getUserRepository(
                    Config.REGION,
                    Config.USER_TABLE
            );

    @Test
    public void updateUserProfile() throws Exception {
        User user = User.of(
                true,
                "Lin",
                "Cenxui",
                "cenxui@gmail.com",
                "A",
                "0928554033"
        );



        System.out.println(JsonUtil.mapToJson(userRepository.updateUserProfile(user)));
    }

    @Test
    public void getUserProfile() throws Exception {

        System.out.println(JsonUtil.mapToJson(userRepository.getUserProfile("cenxui@gmail.com")));
    }

}