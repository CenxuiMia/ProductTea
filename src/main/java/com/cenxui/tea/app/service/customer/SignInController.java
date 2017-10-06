package com.cenxui.tea.app.service.customer;

import com.cenxui.tea.app.service.core.CoreController;
import com.cenxui.tea.app.service.util.Path;
import com.cenxui.tea.app.service.user.UserController;
import com.cenxui.tea.app.service.util.ViewUtil;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class SignInController extends CoreController {
    public static Object serveLoginPage (Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Web.LOGIN);
        //TODO
    }


    public static Object handleLoginPost(Request request, Response response){
        Map<String, Object> model = new HashMap<>();
        if (!UserController.authenticateByUserName(getQueryUsername(request), getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return ViewUtil.render(request, model, Path.Web.LOGIN);
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", getQueryUsername(request));
//        if (getQueryLoginRedirect(request) != null) {
//            response.redirect(getQueryLoginRedirect(request));
//        }
        return ViewUtil.render(request, model, Path.Web.LOGIN);
    }


    public static Object handleLogoutPost (Request request, Response response)  {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };


    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Path.Web.LOGIN);
        }
    };

    private static String getQueryUsername(Request request) {
        //TODO
        return "userName";
    }

    private static String getQueryPassword(Request request) {
        //TODO
        return "password";
    }

}
