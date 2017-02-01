package com.embydextrous.haptikchat.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import com.embydextrous.haptikchat.api.RestClient;
import com.embydextrous.haptikchat.api.response.ChatResponse;
import com.embydextrous.haptikchat.db.ChatDB;
import com.embydextrous.haptikchat.model.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageHelper extends ContextWrapper {

    public MessageHelper(Context base) {
        super(base);
    }

    private OnMessageFetchListener onMessageFetchListener;

    public void fetchMessages(OnMessageFetchListener onMessageFetchListener) {
        this.onMessageFetchListener = onMessageFetchListener;
        try {
            ChatDB chatDB = new ChatDB(getApplicationContext());
            if (chatDB.getMessages().size()>0)
                this.onMessageFetchListener.onMessageFetched(chatDB.getMessages());
            else
                fetchMessagesFromServer();
            chatDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchMessagesFromServer() {
        RestClient.getInstance(getApplicationContext()).getApiService().getChats().enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response!=null && response.body()!=null && response.code()==200) {
                    ArrayList<Message> messages = response.body().getMessages();
                    try {
                        ChatDB chatDb = new ChatDB(getApplicationContext());
                        for (Message message: messages)
                            chatDb.insertMessage(message);
                        onMessageFetchListener.onMessageFetched(chatDb.getMessages());
                        chatDb.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnMessageFetchListener {
        void onMessageFetched(ArrayList<Message> messages);
    }
}
