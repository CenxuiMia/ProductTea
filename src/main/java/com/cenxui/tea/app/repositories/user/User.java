package com.cenxui.tea.app.repositories.user;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> toMap() {
        //TODO to add vale to map

        throw new UnsupportedOperationException("not yet");
    }
}
