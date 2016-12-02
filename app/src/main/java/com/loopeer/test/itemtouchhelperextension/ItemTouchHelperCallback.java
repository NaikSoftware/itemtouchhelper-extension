
package com.loopeer.test.itemtouchhelperextension;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

public class ItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.START);
    }

    @Override
    public float getSwipedTranslationY(RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    @Override
    public float getSwipedTranslationX(RecyclerView.ViewHolder viewHolder) {
        return ViewCompat.getTranslationX(((ViewGroup) viewHolder.itemView).getChildAt(0));
    }

    @Override
    public float getSwipeWidth(RecyclerView.ViewHolder holder) {
        if (holder instanceof MainRecyclerAdapter.ItemSwipeWithActionWidthViewHolder)
            return ((MainRecyclerAdapter.ItemSwipeWithActionWidthViewHolder) holder).mActionContainer.getWidth();
        else if (holder instanceof MainRecyclerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder)
            return ((MainRecyclerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) holder).mActionContainer.getWidth();
        else return super.getSwipeWidth(holder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        MainRecyclerAdapter adapter = (MainRecyclerAdapter) recyclerView.getAdapter();
        adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        MainRecyclerAdapter.ItemBaseViewHolder holder = (MainRecyclerAdapter.ItemBaseViewHolder) viewHolder;
        if (viewHolder instanceof MainRecyclerAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
            if (dX < -holder.mActionContainer.getWidth()) {
                dX = -holder.mActionContainer.getWidth();
            }
            holder.mViewContent.setTranslationX(dX);
            return;
        }
        if (viewHolder instanceof MainRecyclerAdapter.ItemBaseViewHolder)
            holder.mViewContent.setTranslationX(dX);
    }
}
