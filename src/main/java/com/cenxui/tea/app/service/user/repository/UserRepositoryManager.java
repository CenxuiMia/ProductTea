package com.cenxui.tea.app.service.user.repository;

import com.cenxui.tea.app.service.user.User;

public final class UserRepositoryManager implements UserRepository {

    private static final UserRepository manager = new UserRepositoryManager();

    public static UserRepository getManager() {
        return manager;
    }

    @Override
    public User getUserByUserName(String userName) {
        //TODO
        throw new UnsupportedOperationException("not yet");
    }

    @Override
    public User getUserByMail(String mail) {
        //TODO
        throw new UnsupportedOperationException("not yet");

    }

    @Override
    public void setNewHashPasswordAndSaltByUser(String userName, String salt, String newHashPassword) {
        //TODO
        throw new UnsupportedOperationException("not yet");

    }

    @Override
    public void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword) {
        //TODO
        throw new UnsupportedOperationException("not yet");

    }
}
