package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.core.CoreController;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

/**
 * Used as a API for all user table management.
 */

public class UserController extends CoreController {
    private static final UserRepository manager = UserRepositoryManager.getManager();

    public static boolean authenticateByMail(String mail, String password) {
        if (mail == null || mail.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;
        //use mail for query user
        User user = manager.getUserByMail(mail);
        if (user == null) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return hashedPassword.equals(user.getHashedPassword());

    }

    public static void setPasswordByMail(String mail, String oldPassword, String newPassword) {
        if (authenticateByMail(mail, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password by mail
            manager.setNewHashPasswordAndSaltByMail(mail, newSalt, newHashedPassword);
        }
    }

    public static User getUserByMail(String mail) {
        return manager.getUserByMail(mail);
    }


    public static List<User> getUsers() {
        //TODO mia
        return null;
    }

    public static boolean addUser(User user) {
        //TODO mia
        return false;
    }

    public static boolean removeUser(User user) {
        //TODO mia
        return false;
    }

    public static boolean activeUserByMail(String mail) {
        //TODO mia
        return false;
    }

    public static boolean deactiveUserByMail(String mail) {
        //TODO mia
        return false;
    }

 }
