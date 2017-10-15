package com.cenxui.tea.app.repositories.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    Boolean isActive;
    String userName;
    String mail; //primary
    String salt;
    String hashedPassword;
    String addresse;
    String phone;
    String cellphone;
    String token; //token
}
