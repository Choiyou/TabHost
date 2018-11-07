package com.example.os150.tabhost;

import android.app.Activity;

/**
 * Created by leejeongsoon on 2018-11-06.
 * Message Data Set (Message, user)
 */

public class ChatDTO extends Activity{

    private String userName; // username 명칭 정하기
    private String message;

    public ChatDTO(){}
    public ChatDTO(String userName, String message){
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
