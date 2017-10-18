package com.cenxui.tea.app.repositories.user;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

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
