package com.cenxui.tea.app.service.user;

final class UserRepositoryManager implements UserRepository {

    private static final UserRepository manager = new UserRepositoryManager();

    public static UserRepository getManager() {
        return manager;
    }

    private UserRepositoryManager() {}

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
}
