package com.cenxui.tea.app.service.user.repository;

import com.cenxui.tea.app.service.user.User;

import java.util.ArrayList;

public class MockUserRepository implements UserRepository {
    @Override
    public User getUserByUserName(String userName) {
        //password is password
        return User.of( "userName",
         "mail", "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO",
                new ArrayList<>(), "phone", "cellphone");
    }

    @Override
    public User getUserByMail(String mail) {
        //password is password
        return User.of( "userName",
                "mail","$2a$10$e0MYzXyjpJS7Pd0RVvHwHe",
                "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy",
                new ArrayList<>(), "phone", "cellphone");
    }

    @Override
    public void setNewHashPasswordAndSaltByUser(String userName, String salt, String newHashPassword) {

    }

    @Override
    public void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword) {

    }
}
