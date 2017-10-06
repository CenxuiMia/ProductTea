package com.cenxui.tea.app.service.customer;

import com.cenxui.tea.app.service.core.CoreController;
import com.cenxui.tea.app.service.user.repository.UserRepository;
import com.cenxui.tea.app.service.user.repository.UserRepositoryManager;
import spark.Request;
import spark.Response;
import spark.Route;

public class SignUpController extends CoreController {
    private static final UserRepository manager = UserRepositoryManager.getManager();

    public static Route singIn = (Request request, Response response) -> {


        return "success";
    };


}
