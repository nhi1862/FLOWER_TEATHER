package com.java.mju_come.Result;

/**
 * Created by Nahyein on 2018-05-20.
 */


public class Result {
    public String result_name2;
    public String result_flowerlanguage;
    public String result_environment;
    private String result_img;
    private String result_percentage;



    public Result(){}
    public Result(String result_img,String result_name2,String result_flowerlanguage, String result_environment,String result_percentage){
        this.result_img=result_img;
        this.result_name2 =result_name2;
        this.result_flowerlanguage =result_flowerlanguage;
        this.result_environment =result_environment;
        this.result_percentage =result_percentage;

    }

    public String getUrl(){
        return result_img;
    }

    public String getName(){
        return result_name2;
    }
    public String getLanguage(){
        return result_flowerlanguage;
    }
    public String getEnvironment(){
        return result_environment;
    }
    public String getPercentage(){ return result_percentage; }


    public void setUrl(String result_img) {
        this.result_img =result_img;
    }

    public void setName(String result_name2) {
        this.result_name2 =result_name2;
    }
    public void setLanguage(String result_flowerlanguage) {
        this.result_flowerlanguage =result_flowerlanguage;
    }
    public void setEnvironment(String result_environment){
        this.result_environment =result_environment;
    }

    public void setPercentage(String result_percentage){ this.result_percentage =result_percentage; }

}
