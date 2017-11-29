package com.cenxui.tea.app.repositories.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderKey extends Key {
    String mail;
    String time;
}
