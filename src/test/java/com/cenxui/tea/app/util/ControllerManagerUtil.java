package com.cenxui.tea.app.util;

import com.cenxui.tea.app.service.core.Controller;
import com.cenxui.tea.app.service.core.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ControllerManagerUtil {
    public static void mockControllerManager(Class<? extends Controller> controller, String managerFieldName, Repository mockRepository)
            throws Exception {

        Field field = controller.getDeclaredField(managerFieldName);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, mockRepository);
    }
}
