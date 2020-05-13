package com.example.bluetoothtest.player.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerListTouchHelper extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    private boolean longPressEnabled = true;

    public PlayerListTouchHelper(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public interface ItemTouchHelperAdapter {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }

    /**
     * Determines the direction of an event
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // | is bitwise OR operation
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //if (direction == ItemTouchHelper.END) { // if swipe to remove is to the right
            mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        //}
    }

    /**
     * Requires a longer press to start the drag event. Will prevent accidental movements
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * Returns whether ItemTouchHelper should start a swipe to remove operation if a pointer is swiped over the View.
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    public void setLongPressEnabled(boolean longPressEnabled) {
        this.longPressEnabled = longPressEnabled;
    }
}
