package com.cenxui.tea.app.util;

import lombok.Value;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class JavaReflection {

    private Child child;

    @Before
    public void setUp() {
        child = Child.of("John", 14, "2003/1/24");
    }

    @Test
    public void reflectMethod() {


    }

    @Test
    public void reflectField() {
        Arrays.asList(Child.class.getDeclaredFields()).forEach((s) -> {
            System.out.println(s.getName());
            s.setAccessible(true);
            try {
                Object field = s.get(child);

                if (field instanceof String && !Modifier.isStatic(s.getModifiers() )) {
                    System.out.println("String :" + field);
                }else if (field instanceof Number) {
                    System.out.println("Number :" + field);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }

    @Value(staticConstructor = "of")
    static class Child{
        static String WORD = "world";

        String name;
        Integer age;
        String bithday;
    }

}
