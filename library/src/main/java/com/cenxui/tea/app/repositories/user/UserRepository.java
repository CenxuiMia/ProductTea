package com.cenxui.tea.app.repositories.user;

import com.cenxui.tea.app.repositories.Repository;

public interface UserRepository extends Repository {

    User updateUserProfile(User user);

    User getUserProfile(String mail);

    boolean deleteUser(String mail);

}
