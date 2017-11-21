package com.cenxui.tea.app.aws.lambda.event;

import com.cenxui.tea.app.util.JsonUtil;

import java.util.List;

public class Event {
    List<Record> Records;

    @Override
    public String toString() {
        return JsonUtil.mapToJson(this);
    }
}
