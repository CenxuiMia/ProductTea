package com.cenxui.tea.app.service.user;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class User {
    String userName;
    String mail;
    String salt;
    String hashedPassword;
    List<String> addresses;
    String phone;
    String cellphone;

}
