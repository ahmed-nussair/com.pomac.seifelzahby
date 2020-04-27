package com.pomac.seifelzahby.model.responses;

import com.google.gson.annotations.SerializedName;

public class AddingToCartResponse {

    private int status;

    private String message;

    @SerializedName("session_code")
    private String sessionCode;

    @SerializedName("cart_id")
    private int cartId;

    private String[] errors;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public int getCartId() {
        return cartId;
    }

    public String[] getErrors() {
        return errors;
    }
}
