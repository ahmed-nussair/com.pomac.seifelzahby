package com.pomac.seifelzahby.model;

import com.google.gson.annotations.SerializedName;

public class CartItem {

    private int id;

    @SerializedName("product_id")
    private String productId;

    private String quantity;

    @SerializedName("session_code")
    private String sessionCode;

    private Product product;

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public Product getProduct() {
        return product;
    }
}
