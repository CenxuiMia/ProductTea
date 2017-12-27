package com.cenxui.shop.admin.app.util;

import com.cenxui.shop.admin.app.service.AdminController;
import com.cenxui.shop.repositories.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ControllerManagerUtil {
    public static void mockControllerManager(Class<? extends AdminController> controller, String managerFieldName, Repository mockRepository)
            throws Exception {
        Field field = controller.getDeclaredField(managerFieldName);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, mockRepository);
    }
}
