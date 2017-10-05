package com.cenxui.tea.app.service.catagory;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class ProductController {

    public static Route fetchAllProducts = (Request request,  Response response) -> {
       Iterable<Product> products = ProductDAO.getProductDAO().getAllProducts();
       return products;
    };

//    public static Route fetchAllBooks = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);
//        if (clientAcceptsHtml(request)) {
//            HashMap<String, Object> model = new HashMap<>();
//            model.put("books", bookDao.getAllBooks());
//            return ViewUtil.render(request, model, Path.Template.BOOKS_ALL);
//        }
//        if (clientAcceptsJson(request)) {
//            return dataToJson(bookDao.getAllBooks());
//        }
//        return ViewUtil.notAcceptable.handle(request, response);
//    };
//
//    public static Route fetchOneBook = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);
//        if (clientAcceptsHtml(request)) {
//            HashMap<String, Object> model = new HashMap<>();
//            Book book = bookDao.getBookByIsbn(getParamIsbn(request));
//            model.put("book", book);
//            return ViewUtil.render(request, model, Path.Template.BOOKS_ONE);
//        }
//        if (clientAcceptsJson(request)) {
//            return dataToJson(bookDao.getBookByIsbn(getParamIsbn(request)));
//        }
//        return ViewUtil.notAcceptable.handle(request, response);
//    };

}
