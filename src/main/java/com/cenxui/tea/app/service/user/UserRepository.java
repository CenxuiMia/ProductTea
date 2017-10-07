package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.core.Repository;
import com.cenxui.tea.app.service.user.User;

interface UserRepository extends Repository {

    User getUserByMail(String mail) ;

    void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword);

    boolean addUser(User user);

    boolean deleteUserByMail(String mail);

    boolean activeUserByMail(String mail);

    boolean deactiveUserByMail(String mail);

    boolean updateUserDatail(String mail, String cellphone, String phone, String address);

    boolean setUserToken(String token);


}
