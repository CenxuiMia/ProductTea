package com.cenxui.tea.app.repositories.user;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Users {
    List<User> users;
    UserKey key;
}
