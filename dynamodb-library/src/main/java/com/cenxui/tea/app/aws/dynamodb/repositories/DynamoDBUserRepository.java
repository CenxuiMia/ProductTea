package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.cenxui.tea.app.aws.dynamodb.exceptions.client.user.UserProfileException;
import com.cenxui.tea.app.aws.dynamodb.exceptions.client.map.user.UserJsonMapException;
import com.cenxui.tea.app.aws.dynamodb.util.ItemUtil;
import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.repositories.user.UserRepository;
import com.cenxui.tea.app.repositories.user.Users;
import com.cenxui.tea.app.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

final class DynamoDBUserRepository implements UserRepository {
    private final Table userTable;

    DynamoDBUserRepository(Table userTable) {
        this.userTable = userTable;
    }

    @Override
    public User updateUserProfile(User user) {
        PutItemSpec putItemSpec = new PutItemSpec()
                .withItem(ItemUtil.getUserItem(user));

        userTable.putItem(putItemSpec);

        return user;
    }

    @Override
    public User getUserProfile(String mail) {
        QuerySpec querySpec = new QuerySpec();
        querySpec.withHashKey(User.MAIL, mail);

        ItemCollection<QueryOutcome> collection = userTable.query(querySpec);
        List<User> users = new ArrayList<>();
        collection.forEach((s) -> {
            users.add(getUser(s.toJSON()));
        });

        if (users.size() > 1) {
            throw new UserProfileException(users);
        }

        if (users.size() == 1) {
            return users.get(0);
        }
        return null;//no profile
    }

    @Override
    public boolean deleteUser(String mail) {
        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(User.MAIL, mail);
        DeleteItemOutcome outcome = userTable.deleteItem(spec);

        //todo

        return true;
    }

    @Override
    public Users getAllUsers() {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public User getAllUsers(String mail) {
        //todo
        throw new UnsupportedOperationException("not yet");
    }

    private User getUser(String userJson) {
        User user;
        try {
            user = JsonUtil.mapToUser(userJson);
        } catch (Exception e) {
            throw new UserJsonMapException(userJson);
        }
        return user;
    }
}
