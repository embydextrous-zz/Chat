package com.embydextrous.haptikchat.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.embydextrous.haptikchat.R;
import com.embydextrous.haptikchat.db.ChatDB;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserAdapter extends CursorRecyclerViewAdapter {

    public UserAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        UserHolder holder = (UserHolder) viewHolder;
        holder.profileImage.setImageURI(cursor.getString(cursor.getColumnIndex(ChatDB.KEY_IMAGE)), getmContext());
        holder.nameTv.setText(cursor.getString(cursor.getColumnIndex(ChatDB.KEY_SENDER_NAME)));
        holder.usernameTv.setText(cursor.getString(cursor.getColumnIndex(ChatDB.KEY_FROM_USER)));
        holder.totalCountTv.setText(String.valueOf(cursor.getInt(7)));
        holder.favouriteCountTv.setText(String.valueOf(cursor.getInt(8)));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(getmContext()).inflate(R.layout.rv_user_item, parent, false);
        return new UserHolder(v);
    }

    private class UserHolder extends RecyclerView.ViewHolder {

        TextView nameTv, usernameTv, totalCountTv, favouriteCountTv;
        SimpleDraweeView profileImage;
        UserHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            usernameTv = (TextView) itemView.findViewById(R.id.usernameTv);
            totalCountTv = (TextView) itemView.findViewById(R.id.totalCountTv);
            favouriteCountTv = (TextView) itemView.findViewById(R.id.favouriteCountTv);
            profileImage = (SimpleDraweeView) itemView.findViewById(R.id.image);
        }
    }
}
