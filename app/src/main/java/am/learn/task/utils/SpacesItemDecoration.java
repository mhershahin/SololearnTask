package am.learn.task.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpace;
    private final int appBatHeight;

    public SpacesItemDecoration(int space, int appBatHeight) {
        this.mSpace = space;
        this.appBatHeight = appBatHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = appBatHeight+mSpace;
        }
        outRect.bottom = mSpace;
        outRect.left = mSpace;
        outRect.right = mSpace;

    }
}