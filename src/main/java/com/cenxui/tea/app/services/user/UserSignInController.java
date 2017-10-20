package com.cenxui.tea.app.services.user;

import com.cenxui.tea.app.integration.repositories.user.User;
import com.cenxui.tea.app.integration.repositories.user.UserRepository;
import com.cenxui.tea.app.integration.repositories.user.UserRepositoryImpl;
import com.cenxui.tea.app.services.CoreController;
import org.mindrot.jbcrypt.BCrypt;

public class UserSignInController extends CoreController {
    public static final UserRepository manager = UserRepositoryImpl.getManager();

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

}
