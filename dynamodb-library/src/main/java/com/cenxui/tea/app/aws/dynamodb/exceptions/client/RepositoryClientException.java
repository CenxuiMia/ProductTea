package com.cenxui.tea.app.aws.dynamodb.exceptions.client;

public class RepositoryClientException extends RuntimeException {
    protected RepositoryClientException(String e) {
        super(e);
    }
}
