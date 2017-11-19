package com.cenxui.tea.app.aws.dynamodb.exceptions.map;

import com.cenxui.tea.app.aws.dynamodb.exceptions.RepositoryException;

public class JsonMapException extends RepositoryException {
    protected JsonMapException(String e) {
        super(e);
    }
}
