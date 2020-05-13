package com.example.bluetoothtest.player.list;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.player.Host;
import com.example.bluetoothtest.player.Player;

import java.util.Collections;

public class PlayerListAdapter
        extends RecyclerView.Adapter<PlayerListAdapter.PlayerListViewHolder>
        implements PlayerListTouchHelper.ItemTouchHelperAdapter {


    private static final String TAG = "PlayerListAdapter";
    private PlayerOnClick mPlayerOnClick;
    private OnStartDragListener mStartDragListener;
    private Room players;
    private boolean editable;
    private PlayerListRenderer mPlayerListRenderer;




    /**
     * Data structure for list of players
     */
        public class PlayerListViewHolder extends PlayerList.ViewHolder implements View.OnClickListener {
            private static final String TAG = "PlayerListViewHolder";
            private PlayerOnClick playerOnClick;
            public TextView playerName;
            public TextView playerDevice;
            public ImageButton removePlayerButton;
            public ImageButton movePlayerButton;
            public ImageView hostIndicator;
            public RelativeLayout itemLayout;

            @SuppressLint("ClickableViewAccessibility")
            public PlayerListViewHolder(@NonNull View itemView, PlayerOnClick playerOnClick) {
                super(itemView);
                playerName = itemView.findViewById(R.id.player_list_name);
                playerDevice = itemView.findViewById(R.id.player_list_device);
                removePlayerButton = itemView.findViewById(R.id.remove_player_button);
                movePlayerButton = itemView.findViewById(R.id.reorder_player_button);
                hostIndicator = itemView.findViewById(R.id.host_indicator);
                itemLayout = itemView.findViewById(R.id.player_view_layout);

                removePlayerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Log.d(TAG, "onClick: removing " + players.get(position).getName());
                        Log.d(TAG, "onClick: players size " + players.size());
                        Log.d(TAG, "onClick: pressed position " + position + "\n");
                        removeItem(position);
                    }
                });





                this.playerOnClick = playerOnClick;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                playerOnClick.onPlayerClick(getAdapterPosition());

            }
        }

        // This will need to be implemented by whatever view is using the player list. ideally the Player List
        public interface PlayerOnClick {
            void onPlayerClick(int position);
        }
        
        public PlayerListAdapter(PlayerOnClick playerOnClick, OnStartDragListener dragListener, PlayerListRenderer playerListRenderer) {
            players = Room.getInstance();
            mPlayerOnClick = playerOnClick;
            mStartDragListener = dragListener;
            mPlayerListRenderer = playerListRenderer;
        }

        @NonNull
        @Override
        public PlayerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_view_list, parent, false);

            return new PlayerListViewHolder(view, mPlayerOnClick);
        }



        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void onBindViewHolder(final PlayerListViewHolder holder, final int position) {
            Log.d(TAG, "onBindViewHolder: updating bu=ind viewholder for " + players.get(position));

            Player player = players.get(position);
            mPlayerListRenderer.displayItems(holder, player);

            holder.playerName.setText(player.getName());
            holder.playerName.setTextColor(Color.parseColor(player.getSecondaryColor()));
            //holder.playerName.setText(players.get(position).getBtDevice().getName());
            if (editable) {
                holder.movePlayerButton.setVisibility(View.VISIBLE);
                holder.removePlayerButton.setVisibility(View.VISIBLE);

                holder.movePlayerButton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            mStartDragListener.onStartDrag(holder);
                        }
                        return false;
                    }
                });
            } else {
                holder.movePlayerButton.setVisibility(View.INVISIBLE);
                holder.removePlayerButton.setVisibility(View.INVISIBLE);
            }

            if (players.get(position) instanceof Host) holder.hostIndicator.setVisibility(View.VISIBLE);
            else holder.hostIndicator.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount: getting size " + players.size());
            return players.size();
        }

        public void setEditable(boolean b) {
            editable = b;
        }

    public void addItem(Player p) {
        players.add(p);
        notifyItemInserted(players.size());
    }

    public void removeItem(int position) {
        players.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Shifts items up or down depending on where the thing is moved too
     * Gives the list a dynamic feel
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        int delta = (fromPosition < toPosition) ? 1 : -1;

        for (int i = fromPosition; i < toPosition * delta; i += delta) {
            Collections.swap(players, i, i + delta);
        }
        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        players.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnStartDragListener {

        /**
         * Called when a view is requesting a start of a drag.
         *
         * @param viewHolder The holder of the view to drag.
         */
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public void setPlayers(Room players) {
        this.players = players;
        notifyDataSetChanged();
    }

    public void setPlayerListRenderer(PlayerListRenderer mPlayerListRenderer) {
        this.mPlayerListRenderer = mPlayerListRenderer;
    }
}


