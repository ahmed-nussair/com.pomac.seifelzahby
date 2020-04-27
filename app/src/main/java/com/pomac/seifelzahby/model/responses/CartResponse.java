package com.pomac.seifelzahby.model.responses;

import com.pomac.seifelzahby.model.CartItem;

import java.util.List;

public class CartResponse {

    private int status;

    private List<CartItem> data;

    private double total;

    public int getStatus() {
        return status;
    }

    public List<CartItem> getData() {
        return data;
    }

    public double getTotal() {
        return total;
    }
}
