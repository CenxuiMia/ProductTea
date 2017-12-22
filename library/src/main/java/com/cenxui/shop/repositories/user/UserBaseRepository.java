package com.cenxui.shop.repositories.user;

import com.cenxui.shop.repositories.Repository;

public interface UserBaseRepository extends Repository {
    User updateUserProfile(User user);

    User getUserProfile(String mail);

    boolean deleteUser(String mail);

    Users getAllUsers();

    User getUser(String mail);

}
