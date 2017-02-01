package com.embydextrous.haptikchat.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ChatItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalGap;

    public ChatItemDecoration(int mVerticalGap) {
        this.mVerticalGap = mVerticalGap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mVerticalGap;
        outRect.top = mVerticalGap;
    }
}
