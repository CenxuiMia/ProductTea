package com.cenxui.tea.app.aws.dynamodb.exceptions.client.map;

import com.cenxui.tea.app.aws.dynamodb.exceptions.server.RepositoryServerException;

public class JsonMapException extends RepositoryServerException {
    protected JsonMapException(String e) {
        super(e);
    }
}
