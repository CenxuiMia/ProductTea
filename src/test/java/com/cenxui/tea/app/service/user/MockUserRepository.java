package com.cenxui.tea.app.service.user;

import java.util.ArrayList;

public class MockUserRepository implements UserRepository {

    @Override
    public User getUserByMail(String mail) {
        //password is password
        return User.of( Boolean.TRUE, "userName",
                "mail","$2a$10$e0MYzXyjpJS7Pd0RVvHwHe",
                "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy",
                new ArrayList<>(), "phone", "cellphone");
    }

    @Override
    public void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword) {

    }
}
