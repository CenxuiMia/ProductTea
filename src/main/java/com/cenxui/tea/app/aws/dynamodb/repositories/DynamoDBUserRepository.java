package com.cenxui.tea.app.aws.dynamodb.repositories;

import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.repositories.user.UserRepository;

final class DynamoDBUserRepository implements UserRepository {

    @Override
    public User getUserByMail(String mail) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword) {
        //TODO
        throw new UnsupportedOperationException("not yet");

    }

    @Override
    public boolean addUser(User user) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean deleteUserByMail(String mail) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean activeUserByMail(String mail) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean deactiveUserByMail(String mail) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean updateUserDatail(String mail, String cellphone, String phone, String address) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public boolean setUserToken(String token) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }
}
