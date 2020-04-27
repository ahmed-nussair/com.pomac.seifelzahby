package com.pomac.seifelzahby.model.responses;

public class CheckoutResponse {

    private int status;

    private String message;

    private String[] errors;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String[] getErrors() {
        return errors;
    }
}
