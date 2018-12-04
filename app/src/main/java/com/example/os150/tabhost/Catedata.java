package com.example.os150.tabhost;
/*
설명 : 글쓰기의 editText 를 데이터베이스 저장하기위한 재정의 클래스
 */
public class Catedata {
    private String Category;
    private String Price;
    private String Contents;
    private String Title;
    private double Lat;
    private double Lng;
    public Catedata(String Category,String Price,String Contents,String Title,double Lat,double Lng){
        this.Category = Category;
        this.Contents = Contents;
        this.Price = Price;
        this.Title = Title;
        this.Lat = Lat;
        this.Lng = Lng;
    }
    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category){
        this.Category = Category;
    }
    public String getPrice(){
        return  Price;
    }
    public void setPrice(String Price){
        this.Price = Price;
    }
    public String getContents()
    {
        return Contents;
    }
    public void setContents(String Contents){
        this.Contents =Contents;
    }
    public String getTitle(){
        return  Title;
    }
    public void setTitle(String Title){
        this.Title=Title;
    }
    public double getLat(){
        return  Lat;
    }
    public  void setLat(double Lat){
        this.Lat = Lat;
    }
    public double getLng(){
        return Lng;
    }
    public void setLng(double Lng){
        this.Lng = Lng;
    }
}
