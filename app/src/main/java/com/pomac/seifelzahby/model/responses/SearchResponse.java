package com.pomac.seifelzahby.model.responses;

import com.pomac.seifelzahby.model.Product;

import java.util.List;

public class SearchResponse {

    private int status;

    private List<Product> data;

    public int getStatus() {
        return status;
    }

    public List<Product> getData() {
        return data;
    }
}
