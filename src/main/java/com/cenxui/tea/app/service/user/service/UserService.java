package com.cenxui.tea.app.service.user.service;

import com.cenxui.tea.app.service.core.Service;
import com.cenxui.tea.app.service.user.User;

public interface UserService extends Service {

    User getUserByUserName(String userName) ;

    User getUserByMail(String mail) ;

    void setNewHashPasswordAndSaltByUser(String userName, String salt, String newHashPassword);

    void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword);

}
