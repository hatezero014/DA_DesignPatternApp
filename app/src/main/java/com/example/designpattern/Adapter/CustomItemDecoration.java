package com.example.designpattern.Adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int dividerHeight;
    private int[] hideDividerPositions;

    public CustomItemDecoration() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        dividerHeight = 4; // Đặt chiều cao của đường kẻ phân cách
    }

    public void setHideDividerPositions(int... positions) {
        this.hideDividerPositions = positions;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        float dividerWeight = 15f;

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            if (!shouldHideDivider(position)) {
                float left = child.getRight();
                float right = left + dividerHeight;

                float top = child.getTop()+dividerWeight;
                float bottom = child.getBottom()-dividerWeight;

                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();

        if (!shouldHideDivider(itemPosition)) {
            if (itemPosition % spanCount != spanCount - 1) {
                outRect.right = (int) paint.getStrokeWidth(); // Thêm padding bên phải cho mỗi item
            }

            outRect.left = (int) paint.getStrokeWidth(); // Thêm padding bên trái cho mỗi item
        }
    }


    private boolean shouldHideDivider(int position) {
        if (hideDividerPositions != null) {
            for (int hidePosition : hideDividerPositions) {
                if (position == hidePosition) {
                    return true;
                }
            }
        }
        return false;
    }
}