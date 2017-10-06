package com.cenxui.tea.app.service.user.service;

import com.cenxui.tea.app.service.user.User;

public final class UserServices implements UserService {

    private static final UserService service = new UserServices();

    public static UserService getService() {
        return service;
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
