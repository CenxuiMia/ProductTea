package com.cenxui.tea.app.services.user;

import com.cenxui.tea.app.services.CoreController;
import com.cenxui.tea.app.services.Header;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController extends CoreController {
    public static final Route getUserProfile = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);


        throw new UnsupportedOperationException("not yet");
    };

    public static final Route updateUserProfile = (Request request, Response response) -> {
        String mail = request.headers(Header.MAIL);


        throw new UnsupportedOperationException("not yet");
    };
}
