package com.example.quakereport;

public class Earthquake {
    private String mag;
    private String place;
    private String date;
    private String time;
    private String url;
    public Earthquake( String mag,String place,String date,String time,String url){
        this.mag=mag;
        this.place=place;
        this.date=date;
        this.time=time;
        this.url=url;
    }
    public String getMag(){
        return mag;
    }
    public String getPlace(){
        return place;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public String getUrl(){
        return url;
    }
}
