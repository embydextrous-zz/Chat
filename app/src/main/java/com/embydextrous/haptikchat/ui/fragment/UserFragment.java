package com.embydextrous.haptikchat.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.embydextrous.haptikchat.R;
import com.embydextrous.haptikchat.db.ChatDB;
import com.embydextrous.haptikchat.ui.adapter.UserAdapter;

public class UserFragment extends Fragment {

    private UserAdapter userAdapter;
    private ChatDB chatDB;

    public static final String FAVOURITE_INTENT_FILTER = "favourite_intent_filter";

    private BroadcastReceiver favouriteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (userAdapter!=null)
               swapCursor();
        }
    };

    private void swapCursor() {
        if (chatDB==null)
            chatDB = new ChatDB(getActivity().getApplicationContext());
        Cursor c = chatDB.getUserCursor();
        userAdapter.swapCursor(c);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView usersRv = (RecyclerView) view.findViewById(R.id.rv);
        try {
            chatDB = new ChatDB(getActivity().getApplicationContext());
            Cursor c = chatDB.getUserCursor();
            userAdapter = new UserAdapter(getActivity(), c);
            usersRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            usersRv.setAdapter(userAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(favouriteChangeReceiver, new IntentFilter(FAVOURITE_INTENT_FILTER));
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(favouriteChangeReceiver);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        userAdapter.getCursor().close();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (chatDB!=null)
            chatDB.close();
        super.onDestroy();
    }
}
