package com.cenxui.tea.app.aws.dynamodb.exceptions;

public class ProductMapJsonException extends RepoistoryException{
    public ProductMapJsonException() {
        super("Product list map to json fail");
    }
}
