package com.embydextrous.haptikchat.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable {

    @SerializedName("body")
    private String body;
    @SerializedName("username")
    private String userName;
    @SerializedName("image-url")
    private String imageUrl;
    @SerializedName("message-time")
    private String timeStamp;
    @SerializedName("Name")
    private String name;
    private boolean isFavourite;

    private int id;

    public Message(int id, String body, String userName, String imageUrl, String timeStamp, String name, boolean isFavourite) {
        this.id = id;
        this.body = body;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.timeStamp = timeStamp;
        this.name = name;
        this.isFavourite = isFavourite;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
