package com.cenxui.tea.app.service.admin;

import com.cenxui.tea.app.service.core.CoreController;
import com.cenxui.tea.app.service.user.User;
import com.cenxui.tea.app.service.user.UserController;

import java.util.List;

public class ManageUserController extends CoreController {

    public static List<User> getUsers() {
        return UserController.getUsers();
    }

    public static User getUserByMail(String mail) {
        return UserController.getUserByMail(mail);
    }


}
