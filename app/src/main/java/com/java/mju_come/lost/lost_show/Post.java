package com.java.mju_come.lost.lost_show;

import com.google.firebase.database.Exclude;

public class Post {
    private String id;
    private String category;
    private String label;
    private String location;
    private String textbox;
    private String url;

    @Exclude
    public String ignoreThisField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTextbox(String textbox) {
        this.textbox = textbox;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public String getLocation() {
        return location;
    }

    public String getTextbox() {
        return textbox;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Post() {
    }



}
