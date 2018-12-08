package com.example.os150.tabhost;
/*
설명 : 글쓰기의 editText 를 데이터베이스 저장하기위한 재정의 클래스
 */
public class Towndata {
    private String Townname;
    private double Lat2;
    private double Lng2;
    private String UserName;
    public Towndata(String Townname,String UserName, double Lat2,double Lng2){

        this.Townname = Townname;
        this.UserName = UserName;
        this.Lat2 = Lat2;
        this.Lng2 = Lng2;
    }
    public String getTownname(){
        return Townname;
    }
    public void setTownname(String Townname){
        this.Townname = Townname;
    }

    public String getUserName(){
        return  UserName;
    }
    public void setUserName(String UserName){
        this.UserName = UserName;
    }

    public double getLat2(){
        return  Lat2;
    }
    public  void setLat2(double Lat2){
        this.Lat2 = Lat2;
    }
    public double getLng2(){
        return Lng2;
    }
    public void setLng2(double Lng2){
        this.Lng2 = Lng2;
    }
}
