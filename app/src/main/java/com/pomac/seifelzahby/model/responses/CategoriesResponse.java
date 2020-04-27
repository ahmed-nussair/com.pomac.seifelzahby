package com.pomac.seifelzahby.model.responses;

import com.pomac.seifelzahby.model.Category;

import java.util.List;

public class CategoriesResponse {

    private int status;
    private List<Category> data;

    public int getStatus() {
        return status;
    }

    public List<Category> getData() {
        return data;
    }
}
