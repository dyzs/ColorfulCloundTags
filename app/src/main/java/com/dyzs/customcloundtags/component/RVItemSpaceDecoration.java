package com.dyzs.customcloundtags.component;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by maidou on 2016/4/11.
 */
public class RVItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public RVItemSpaceDecoration(int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
