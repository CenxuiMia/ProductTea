package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.core.CoreController;
import com.cenxui.tea.app.service.user.repository.UserRepository;
import com.cenxui.tea.app.service.user.repository.UserRepositoryManager;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserController extends CoreController {
    private static final UserRepository manager = UserRepositoryManager.getManager();

    public static boolean authenticateByUserName(String userName, String password) {
        if (userName == null || userName.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;

        //use user name for query user
        User user = manager.getUserByUserName(userName);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return hashedPassword.equals(user.getHashedPassword());
    }

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

    public static void setPasswordByUserName(String userName, String oldPassword, String newPassword) {
        if (authenticateByUserName(userName, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password by user
            manager.setNewHashPasswordAndSaltByUser(userName, newSalt, newHashedPassword);
        }
    }

    public static void setPasswordByMail(String mail, String oldPassword, String newPassword) {
        if (authenticateByMail(mail, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password by mail
            manager.setNewHashPasswordAndSaltByMail(mail, newSalt, newHashedPassword);
        }
    }

    public List<User> getUsers() {
        //TODO
        return null;
    }

    public static boolean addUser() {
        //TODO
        return false;
    }

    public static boolean removeUser() {
        //TODO
        return false;
    }
 }
