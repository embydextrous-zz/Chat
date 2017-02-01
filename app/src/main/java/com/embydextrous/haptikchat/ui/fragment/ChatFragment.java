package com.embydextrous.haptikchat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.embydextrous.haptikchat.R;
import com.embydextrous.haptikchat.helper.ChatItemDecoration;
import com.embydextrous.haptikchat.helper.MessageHelper;
import com.embydextrous.haptikchat.helper.Util;
import com.embydextrous.haptikchat.model.Message;
import com.embydextrous.haptikchat.ui.adapter.ChatAdapter;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements MessageHelper.OnMessageFetchListener {

    private ChatAdapter chatAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView chatsRv = (RecyclerView) view.findViewById(R.id.rv);
        chatAdapter = new ChatAdapter(getActivity());
        chatsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        chatsRv.setAdapter(chatAdapter);
        chatsRv.addItemDecoration(new ChatItemDecoration(Util.dp2px(12)));
    }

    @Override
    public void onResume() {
        super.onResume();
        MessageHelper messageHelper = new MessageHelper(getActivity());
        messageHelper.fetchMessages(this);
    }

    @Override
    public void onMessageFetched(ArrayList<Message> messages) {
        chatAdapter.setData(messages);
    }
}
