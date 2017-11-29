package com.cenxui.tea.app.repositories.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserKey {
    String mail;
}
