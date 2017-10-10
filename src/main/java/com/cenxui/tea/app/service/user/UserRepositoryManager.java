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
