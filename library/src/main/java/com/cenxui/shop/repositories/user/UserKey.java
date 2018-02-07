package com.cenxui.shop.repositories.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserKey extends Key {
    String mail;
}
