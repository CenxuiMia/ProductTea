package com.cenxui.tea.app.integration.repositories.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    //TODO MIA add public static final string for the fields name

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
