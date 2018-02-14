package com.cenxui.shop.repositories.point;

import lombok.Value;

@Value(staticConstructor = "of")
public class Point {
    public static final String MAIL = "mail";
    public static final String VALUE = "point";

    String mail;
    String point;
}
