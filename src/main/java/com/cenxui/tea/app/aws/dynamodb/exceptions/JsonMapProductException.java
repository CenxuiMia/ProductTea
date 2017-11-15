package com.cenxui.tea.app.aws.dynamodb.exceptions;

public class JsonMapProductException extends RepoistoryException {
    public JsonMapProductException() {
        super("JSon value mapping to Product object fail ");
    }
}
