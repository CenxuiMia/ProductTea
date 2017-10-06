package com.cenxui.tea.app;

import com.cenxui.tea.app.service.catagory.ProductCoreController;
import com.cenxui.tea.app.service.util.Path;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        int sum = 15;
        System.out.printf("The total meal cost is %d dollars.", sum);

        //config
        port(9000);

        get(Path.Web.PRODUCTS, ProductCoreController.fetchAllProducts);

//        get(Path.Web.HOME, (req, res) -> IndexController.serveHomePage(req, res), new HandlebarsTemplateEngine());

        //handle auth routes

//        get(Path.Web.GET_LOGIN_PAGE, (req, res) -> SignInController.serveLoginPage(req, res), new HandlebarsTemplateEngine());
//
//        post(Path.Web.DO_LOGIN, (req, res) -> { return SignInController.handleLogin(req, res);});
//
//        post(Path.Web.DO_AUTH, (req, res) -> { return AuthController.handleAuth(req, res); });
//
//        get(Path.Web.GET_SIGN_UP, (req, res) -> { return AuthController.serveSignUpPage(req, res); }, new HandlebarsTemplateEngine());
//
//        post(Path.Web.DO_SIGN_UP, (req, res) -> { return AuthController.handleSignUp(req, res);});
//
//        get(Path.Web.LOGOUT, (req, res) -> { return AuthController.handleSignOut(req, res); });

//
//        //handle CRUD routes
//
//        get(Path.Web.DASHBOARD, (req, res) -> { return ContactController.serveDashboard(req, res);}, new HandlebarsTemplateEngine());
//
//        delete(Path.Web.DELETE, (req, res)-> { return ContactController.handleDeleteContact(req, res);}, new JsonTransformer());
//
//        put(Path.Web.UPDATE, "application/json", (req, res) -> { return ContactController.handleUpdateContact(req, res); });
//
//        post(Path.Web.NEW, "application/json", (req, res) -> { return ContactController.handleNewContact(req, res);});

    }

}
