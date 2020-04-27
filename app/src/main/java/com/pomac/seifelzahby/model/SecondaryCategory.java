package com.pomac.seifelzahby.model;

import com.google.gson.annotations.SerializedName;

public class SecondaryCategory {
    private int id;

    private String name;

    @SerializedName("main_specialist_id")
    private String mainId;

    private String imagePath;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMainId() {
        return mainId;
    }

    public String getImagePath() {
        return imagePath;
    }
}
