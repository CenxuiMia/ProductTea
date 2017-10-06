package com.cenxui.tea.app.service.user;

import com.cenxui.tea.app.service.core.ControllerImpl;
import com.cenxui.tea.app.service.user.service.UserService;
import com.cenxui.tea.app.service.user.service.UserServices;
import org.mindrot.jbcrypt.BCrypt;

public class UserController extends ControllerImpl {
    private static final UserService service = UserServices.getService();

    public static boolean authenticateByUserName(String userName, String password) {
        if (userName == null || userName.isEmpty()) return false;
        if (password == null || password.isEmpty()) return false;

        //use user name for query user
        User user = service.getUserByUserName(userName);
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
        User user = service.getUserByMail(mail);
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
            service.setNewHashPasswordAndSaltByUser(userName, newSalt, newHashedPassword);
        }
    }

    public static void setPasswordByMail(String mail, String oldPassword, String newPassword) {
        if (authenticateByMail(mail, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password by mail
            service.setNewHashPasswordAndSaltByMail(mail, newSalt, newHashedPassword);
        }
    }
}
