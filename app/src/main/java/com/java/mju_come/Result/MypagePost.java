package com.java.mju_come.Result;

import com.google.firebase.database.Exclude;

/**
 * Created by Nahyein on 2018-05-31.
 */


public class MypagePost {
    private String id;
    private String flowername;
    private String flowerlanguage;
    private String environment;
    private String img;
    private String percentage;

    @Exclude
    public String ignoreThisField;

    public String getFlowername() {return flowername;}
    public void setFlowername(String flowername) {
        this.flowername = flowername;
    }
    public String getFlowerlanguage() {return flowerlanguage;}
    public void setFlowerlanguage(String flowerlanguage) {
        this.flowerlanguage = flowerlanguage;
    }
    public String getEnvironment() {return environment;}
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    public String getImg() {return img;}
    public void setImg(String img) {
        this.img = img;
    }
    public String getPercentage(){return percentage;}
    public void setPercentage(String percentage){this.percentage=percentage;}

    public String getId(){return id;}
    public void setId(String id){this.id=id;}


    public MypagePost() {
    }



}
