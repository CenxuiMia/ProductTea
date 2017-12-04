package com.cenxui.tea.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonTestUtil {
    public static OrderTestResult mapToOrderTestResult(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, OrderTestResult.class);

    }

}
