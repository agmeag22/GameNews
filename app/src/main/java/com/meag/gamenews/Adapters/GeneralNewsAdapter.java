package com.meag.gamenews.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.meag.gamenews.ForAPI.New;
import com.meag.gamenews.R;

import java.util.List;

public class GeneralNewsAdapter extends RecyclerView.Adapter<GeneralNewsAdapter.GeneralNewsViewHolder> {
    private List<New> newList; // Cached copy of words

    public GeneralNewsAdapter(List<New> newList) {
        this.newList = newList;
    }

    @Override
    public GeneralNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cardview, parent, false);
        return new GeneralNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GeneralNewsViewHolder holder, int position) {
        if (newList != null) {
            if (newList.get(position).getCoverImage() != null) {
                // holder.newspic=set
            }

        } else {

        }
    }


    // getItemCount() is called many times, and when it is first called,
    // newList has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (newList != null)
            return newList.size();
        else return 0;
    }

    class GeneralNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newspic;
        ImageButton newsfavoritemarker;
        TextView newsTitle;

        private GeneralNewsViewHolder(View itemView) {
            super(itemView);
            newspic = itemView.findViewById(R.id.image_news);
            newsfavoritemarker = itemView.findViewById(R.id.news_favorite);
            newsTitle = itemView.findViewById(R.id.title_news);
        }
    }
}