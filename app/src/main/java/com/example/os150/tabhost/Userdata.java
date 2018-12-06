package com.example.os150.tabhost;

/**
 * Created by os150 on 2018-11-22.
 */

public class Userdata {
    public String userName;
    public String profile;
    public String userEmail;
    public String uid;

    public Userdata(String userName,String profile,String userEmail){
        this.userName = userName;
        this.profile = profile;
        this.userEmail = userEmail;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getProfile(){
        return profile;
    }
    public void setProfile(String profile){
        this.profile = profile;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
}


