package com.example.os150.tabhost;

public class MyItem {

    private String PostUid;
    private String Title;
    private String Price;
    private String Contents;
    private String Category;
    private String UserUid;
    private String UserName;


    public String getTitle(){
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
    public String getPrice(){
        return Price;
    }
    public void setPrice(String Price){
        this.Price=Price;
    }
    public String getContents(){
        return Contents;
    }

    public void setContents(String Contents) {
        this.Contents = Contents;
    }
    public String getCategory(){
        return Category;
    }
    public void setCategory(String Category){
        this.Category = Category;
    }
    public String getUserUid(){
        return  UserUid;
    }
    public void setUserUid(String UserUid){
        this.UserUid = UserUid;
    }
    public String getUserName(){
        return UserName;
    }
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
}
