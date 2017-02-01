package com.embydextrous.haptikchat.api.response;

import com.embydextrous.haptikchat.model.Message;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatResponse implements Serializable {

    @SerializedName("count")
    private int count;

    @SerializedName("messages")
    private ArrayList<Message> messages;

    public ChatResponse(int count, ArrayList<Message> messages) {
        this.count = count;
        this.messages = messages;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
