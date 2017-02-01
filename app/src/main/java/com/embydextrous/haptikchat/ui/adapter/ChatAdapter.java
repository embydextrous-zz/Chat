package com.embydextrous.haptikchat.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.embydextrous.haptikchat.R;
import com.embydextrous.haptikchat.db.ChatDB;
import com.embydextrous.haptikchat.helper.Util;
import com.embydextrous.haptikchat.model.Message;
import com.embydextrous.haptikchat.ui.fragment.UserFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private Context context;
    private ArrayList<Message> messages = new ArrayList<>();

    public ChatAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatHolder(LayoutInflater.from(context).inflate(R.layout.rv_chatitem, parent, false), context);
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        Message message = messages.get(position);
        holder.senderTv.setText(message.getName());
        holder.messageTv.setText(message.getBody());
        holder.image.setImageURI(message.getImageUrl(), this);
        holder.toggleButton.setChecked(message.isFavourite());
        holder.toggleButton.setButtonDrawable(message.isFavourite()? AppCompatResources.getDrawable(context, R.drawable.wishlist_action_icon_selected):AppCompatResources.getDrawable(context, R.drawable.wishlist_action_icon_deselected));
    }

    public void setData(ArrayList<Message> messages) {
        this.messages.clear();
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder {

        TextView senderTv, messageTv;
        SimpleDraweeView image;
        View wrapper;
        ToggleButton toggleButton;
        Context context;

        ChatHolder(View itemView, final Context context) {
            super(itemView);
            this.context = context;
            senderTv = (TextView) itemView.findViewById(R.id.senderTv);
            messageTv = (TextView) itemView.findViewById(R.id.messageTv);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggle);
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messages.get(getAdapterPosition()).setFavourite(toggleButton.isChecked());
                    notifyItemChanged(getAdapterPosition());
                    Intent intent = new Intent();
                    intent.setAction(UserFragment.FAVOURITE_INTENT_FILTER);
                    context.sendBroadcast(intent);
                    try {
                        ChatDB chatDb = new ChatDB(context);
                        chatDb.markFavourite(messages.get(getAdapterPosition()));
                        chatDb.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            wrapper = itemView.findViewById(R.id.wrapper);
            int mWidth = (int) (Util.getScreenWidth(context)*0.3);
            wrapper.setMinimumWidth(mWidth);
        }
    }
}
