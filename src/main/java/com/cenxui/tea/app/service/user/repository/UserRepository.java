package com.cenxui.tea.app.service.user.repository;

import com.cenxui.tea.app.service.core.Repository;
import com.cenxui.tea.app.service.user.User;

public interface UserRepository extends Repository {

    User getUserByUserName(String userName) ;

    User getUserByMail(String mail) ;

    void setNewHashPasswordAndSaltByUser(String userName, String salt, String newHashPassword);

    void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword);

}
