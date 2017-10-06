package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.core.Repository;
import com.cenxui.tea.app.service.user.User;

interface UserRepository extends Repository {

    User getUserByMail(String mail) ;

    void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword);

}
