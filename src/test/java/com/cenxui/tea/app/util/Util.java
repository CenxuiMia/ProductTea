package com.cenxui.tea.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class Util {

    static void setStaticFinal(Class<?> clazz, String declaredFieldName, Object newValue) throws Exception {
        Field field = clazz.getDeclaredField(declaredFieldName);

        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
