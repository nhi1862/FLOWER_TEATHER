package com.java.mju_come.lost.enroll;


public class Lost {
    public String label;
    public String category;
    public String location;
    public String textbox;
    public String url;

    //위도, 경도 추가
    public String latitude;
    public String longitude;

    public Lost(){}
    public Lost(String label, String category, String location, String textbox, String url, String latitude, String longitude){
        this.label =label;
        this.category =category;
        this.location =location;
        this.textbox =textbox;
        this.url =url;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getLabel(){
        return label;
    }
    public String getCategory(){
        return category;
    }
    public String getLocation(){
        return location;
    }
    public String getTextbox(){
        return textbox;
    }
    public String getUrl(){
        return url;
    }
    public String getLatitude(){ return latitude; }
    public String getLongitude(){ return longitude; }

}
