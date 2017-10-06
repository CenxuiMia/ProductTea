package com.cenxui.tea.app.util;

import com.cenxui.tea.app.service.core.Controller;
import com.cenxui.tea.app.service.core.Repository;

public class ControllerServiceUtil {
    public static void mockControllerService(Class<? extends Controller> controller, String managerFieldName, Repository mockRepository)
            throws Exception {
       Util.setStaticFinal(controller, managerFieldName, mockRepository);
    }
}
