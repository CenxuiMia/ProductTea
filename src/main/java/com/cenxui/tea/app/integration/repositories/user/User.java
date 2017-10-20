package com.cenxui.tea.app.integration.repositories.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    public static final String IS_ACTIVE = "isActive";
    public static final String USER_NAME = "userName";
    public static final String MAIL = "mail";
    public static final String SALT = "salt";
    public static final String HASHED_PASSWORD = "hashedPassword";
    public static final String ADDRESSES = "addresses";
    public static final String PHONE = "phone";
    public static final String CELLPHONE = "cellphone";
    public static final String TOKEN = "token";

    Boolean isActive;
    String userName;
    String mail; //primary
    String salt;
    String hashedPassword;
    String addresses;
    String phone;
    String cellphone;
    String token; //token

}
