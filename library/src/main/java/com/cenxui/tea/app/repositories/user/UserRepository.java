package com.cenxui.tea.app.repositories.user;

import com.cenxui.tea.app.repositories.Repository;

public interface UserRepository extends Repository {

    User getUserByMail(String mail) ;

    void setNewHashPasswordAndSaltByMail(String mail, String salt, String newHashPassword);

    boolean addUser(User user);

    boolean deleteUserByMail(String mail);

    boolean activeUserByMail(String mail);

    boolean deActiveUserByMail(String mail);

    boolean updateUserDetail(String mail, String cellphone, String phone, String address);

    boolean setUserToken(String token);


}
