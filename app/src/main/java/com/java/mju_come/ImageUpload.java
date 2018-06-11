package com.java.mju_come;


//이미지 개체
public class ImageUpload {

    public String name;
    public String url;
    public String location;
    public double latitude;
    public double longitude;

    public ImageUpload(){}
    public ImageUpload(String name, String url, double longitude, double latitude){
        this.name =name;
        this.url =url;
        this.longitude =longitude;
        this.latitude =latitude;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

}
