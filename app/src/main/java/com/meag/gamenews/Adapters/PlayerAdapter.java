package com.meag.gamenews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meag.gamenews.Database.New;
import com.meag.gamenews.Database.Player;
import com.meag.gamenews.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.GeneralNewsViewHolder> {
    private List<Player> playerList; // Cached copy of words
    private Context context;

    public PlayerAdapter(Context context) {
        this.context = context;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public GeneralNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_cardview, parent, false);
        return new GeneralNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GeneralNewsViewHolder holder, final int position) {
        if (playerList != null) {
            if (playerList.get(position).getAvatar() != null) {
                Glide.with(context).load(playerList.get(position).getAvatar()).apply(RequestOptions.centerCropTransform()).into(holder.avatar);
            }
            if (playerList.get(position).getName() != null) {
                holder.name.setText(playerList.get(position).getName());
            }

        }

    }


    // getItemCount() is called many times, and when it is first called,
    // playerList has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (playerList != null)
            return playerList.size();
        else return 0;
    }

    class GeneralNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;

        private GeneralNewsViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.cardpicture_imageview);
            name = itemView.findViewById(R.id.player_name);
        }
    }
}