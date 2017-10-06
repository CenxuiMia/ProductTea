package com.cenxui.tea.app.util;

import com.cenxui.tea.app.service.core.Controller;
import com.cenxui.tea.app.service.core.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ControllerServiceUtil {
    public static void mockControllerService(Class<? extends Controller> controller, String serviceFieldName, Service mockService)
            throws Exception {
        Field field = controller.getDeclaredField(serviceFieldName);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, mockService);
    }
}
