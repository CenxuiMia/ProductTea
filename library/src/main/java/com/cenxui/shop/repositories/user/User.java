package com.cenxui.shop.repositories.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    public static final String IS_ACTIVE = "isActive";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MAIL = "mail";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";

    Boolean isActive;
    String firstName;
    String lastName;
    String mail; //primary
    String address;
    String phone;

}
