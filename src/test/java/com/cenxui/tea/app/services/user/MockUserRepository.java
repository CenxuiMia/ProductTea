package com.cenxui.tea.app.services.user;

import com.cenxui.tea.app.repositories.user.User;
import com.cenxui.tea.app.repositories.user.UserRepository;

public class MockUserRepository implements UserRepository {

    @Override
    public User getUserByMail(String mail) {
        //password is password
        return User.of( Boolean.TRUE, "userName",
                "mail","$2a$10$e0MYzXyjpJS7Pd0RVvHwHe",
                "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy",
                "", "phone", "cellphone",
                "");
    }

    @Override
    public void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword) {

    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUserByMail(String mail) {
        return false;
    }

    @Override
    public boolean activeUserByMail(String mail) {
        return false;
    }

    @Override
    public boolean deActiveUserByMail(String mail) {
        return false;
    }

    @Override
    public boolean updateUserDetail(String mail, String cellphone, String phone, String address) {
        return false;
    }

    @Override
    public boolean setUserToken(String token) {
        return false;
    }
}
