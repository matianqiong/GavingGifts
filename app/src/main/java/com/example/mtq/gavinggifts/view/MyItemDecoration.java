package com.example.mtq.gavinggifts.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mtq on 2016/8/21.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
   private int space;

    public MyItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom=space;
        outRect.right=space;
        outRect.left=space;
        if (parent.getChildPosition(view)==0){
            outRect.top=space;
        }
    }
}
