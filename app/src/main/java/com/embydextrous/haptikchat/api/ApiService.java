package com.embydextrous.haptikchat.api;

import com.embydextrous.haptikchat.api.response.ChatResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("test_data")
    Call<ChatResponse> getChats();
}
