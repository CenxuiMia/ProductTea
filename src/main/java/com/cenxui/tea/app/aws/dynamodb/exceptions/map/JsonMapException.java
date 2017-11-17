package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepoistoryException;

public class JsonMapException extends RepoistoryException {
    public JsonMapException(String e) {
        super(e);
    }
}
