package com.example.tanga.driverprotection;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("idUser")
    private String nickname;
    @SerializedName("message")
    private String message ;

    public  Message(){

    }
    public Message(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}