package com.pomac.seifelzahby.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    private int id;

    private String name;

    private String description;

    private String price;

    private String imagePath;

    @SerializedName("categoryname")
    private String categoryName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
