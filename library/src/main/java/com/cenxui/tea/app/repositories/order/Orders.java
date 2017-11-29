package com.cenxui.tea.app.repositories.order;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Orders {
    List<Order> orders;
    Key key;
}
