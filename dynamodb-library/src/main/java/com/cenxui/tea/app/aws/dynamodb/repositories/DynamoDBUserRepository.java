package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.amazonaws.services.dynamodbv2.document.*;
import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.repositories.user.UserBaseRepository;
import com.cenxui.tea.app.repositories.user.UserRepository;
import com.cenxui.tea.app.repositories.user.Users;


final class DynamoDBUserRepository implements UserRepository {
    private final UserBaseRepository userBaseRepository;

    DynamoDBUserRepository(Table userTable) {
        this.userBaseRepository = new DynamoDBUserBaseRepository(userTable);
    }

    @Override
    public User updateUserProfile(User user) {
        return userBaseRepository.updateUserProfile(user);
    }

    @Override
    public User getUserProfile(String mail) {
        return userBaseRepository.getUserProfile(mail);
    }

    @Override
    public boolean deleteUser(String mail) {
        return userBaseRepository.deleteUser(mail);
    }

    @Override
    public Users getAllUsers() {
        return userBaseRepository.getAllUsers();
    }

    @Override
    public User getUser(String mail) {
        return userBaseRepository.getUser(mail);
    }
}
