package com.pomac.seifelzahby.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    private int id;

    private String name;

    private String image;

    private String imagePath;

    @SerializedName("get_secondary")
    private List<SecondaryCategory> secondaryCategories;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<SecondaryCategory> getSecondaryCategories() {
        return secondaryCategories;
    }
}
