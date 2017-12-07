package com.cenxui.tea.app.aws.dynamodb.exceptions.client.map;

import com.cenxui.tea.app.aws.dynamodb.exceptions.client.RepositoryClientException;

public class JsonMapException extends RepositoryClientException {
    protected JsonMapException(String e) {
        super(e);
    }
}
