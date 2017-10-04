package com.cenxui.tea.app.service.util;

import spark.Request;

import java.util.Map;

public class ViewUtil {
    public static String render(Request request, Map model, String templatePath) {
//        model.put("msg", new MessageBundle(getSessionLocale(request)));
//        model.put("currentUser", getSessionCurrentUser(request));
//        model.put("WebPath", Path.Web.class); // Access application URLs from templates
//        return strictVelocityEngine().render(new ModelAndView(model, templatePath));

        //TODO
        return "view util";
    }
}
