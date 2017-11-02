package com.cenxui.tea.app;

import com.cenxui.tea.app.services.product.ProductController;
import com.cenxui.tea.app.services.util.Path;

import static spark.Spark.*;

public final class Application {
    public static void main(String[] args) {

        //config
        port(9000);
        defineResources();
    }

    public static void defineResources() {
        get(Path.Web.PRODUCT, ProductController.fetchAllProducts);
        get(Path.Web.INDEX, (req, rep) -> {
            return "Hello World";
        });
    }

}
