package com.cenxui.shop.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.cenxui.shop.aws.dynamodb.exceptions.client.map.user.UserJsonMapException;
import com.cenxui.shop.aws.dynamodb.exceptions.server.user.UserPrimaryKeyCannotEmptyException;
import com.cenxui.shop.aws.dynamodb.exceptions.client.user.UserProfileException;
import com.cenxui.shop.aws.dynamodb.util.ItemUtil;
import com.cenxui.shop.repositories.user.User;
import com.cenxui.shop.repositories.user.UserBaseRepository;
import com.cenxui.shop.repositories.user.UserKey;
import com.cenxui.shop.repositories.user.Users;
import com.cenxui.shop.util.JsonUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class DynamoDBUserBaseRepository implements UserBaseRepository {
    private final Table userTable;

    DynamoDBUserBaseRepository(Table userTable) {
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
        checkPrimaryKey(mail);

        QuerySpec querySpec = new QuerySpec();
        querySpec.withHashKey(User.MAIL, mail);

        ItemCollection<QueryOutcome> collection = userTable.query(querySpec);
        List<User> users = new ArrayList<>();
        collection.forEach((s) -> {
            users.add(mapToUser(s.toJSON()));
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
        checkPrimaryKey(mail);

        DeleteItemSpec spec = new DeleteItemSpec()
                .withPrimaryKey(User.MAIL, mail);
        DeleteItemOutcome outcome = userTable.deleteItem(spec);

        //todo

        return true;
    }

    @Override
    public Users getAllUsers() {
        ScanSpec scanSpec = new ScanSpec();
        ItemCollection<ScanOutcome> collection = userTable.scan(scanSpec);
        List<User> users = mapScanOutcomeToUser(collection);
        UserKey userKey = getUserLastKey(
                collection.getLastLowLevelResult().getScanResult().getLastEvaluatedKey());

        return Users.of(users, userKey);
    }


    @Override
    public User getUser(String mail) {
        QuerySpec spec = new QuerySpec()
                .withHashKey(User.MAIL, mail);

        ItemCollection<QueryOutcome> collection = userTable.query(spec);
        List<User> users = mapQueryOutcomeToUser(collection);

        if (users.size() == 1) {
            return users.get(0);
        }

        return null;
    }


    private List<User> mapQueryOutcomeToUser(ItemCollection<QueryOutcome> collection) {
        List<User> users = new LinkedList<>();

        collection.forEach((s) -> {
            mapToUser(s.toJSON());}
        );
        return users;
    }

    private List<User> mapScanOutcomeToUser(ItemCollection<ScanOutcome> collection) {
        List<User> users = new LinkedList<>();

        collection.forEach((s) -> {
                users.add(mapToUser(s.toJSON()));}
        );
        return users;
    }

    private UserKey getUserLastKey(Map<String, AttributeValue> lastEvaluedKey ) {

        if (lastEvaluedKey != null) {
            return UserKey.of(lastEvaluedKey.get(User.MAIL).getS());
        }

        return null;
    }


    private User mapToUser(String userJson) {
        User user;
        try {
            user = JsonUtil.mapToUser(userJson);
        } catch (Exception e) {
            throw new UserJsonMapException(userJson);
        }
        return user;
    }

    private void checkPrimaryKey(String key) {
        if (key == null || key.length() == 0) {
            throw new UserPrimaryKeyCannotEmptyException();
        }
    }
}
