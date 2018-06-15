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
import com.meag.gamenews.Database.Repository;
import com.meag.gamenews.R;

import java.util.List;

public abstract class GeneralNewsAdapter extends RecyclerView.Adapter<GeneralNewsAdapter.GeneralNewsViewHolder> {
    private List<New> newList; // Cached copy of words
    private Context context;
    private Repository repository;

    public GeneralNewsAdapter(Context context) {
        this.context = context;

    }

    public List<New> getNewList() {
        return newList;
    }

    public void setNewList(List<New> newList) {
        this.newList = newList;
    }

    @Override
    public GeneralNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cardview, parent, false);
        return new GeneralNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GeneralNewsViewHolder holder, final int position) {
        if (newList != null) {
            if (newList.get(position).getTitle() != null) {
                holder.newsTitle.setText(newList.get(position).getTitle());
            }
            if (newList.get(position).getDescription() != null) {
                holder.newsDescription.setText(newList.get(position).getDescription());
            }
            if (newList.get(position).getCoverImage() != null) {
                Glide.with(context).load(newList.get(position).getCoverImage()).apply(RequestOptions.centerCropTransform()).into(holder.newspic);
            }
            if (newList.get(position).isFavorite()) {
                holder.newsfavoritemarker.setImageResource(android.R.drawable.star_big_on);
            } else {
                holder.newsfavoritemarker.setImageResource(android.R.drawable.star_big_off);
            }
            holder.newsfavoritemarker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (newList.get(position).isFavorite()) {

                        setFavoriteOff(newList.get(position).getId());
                        holder.newsfavoritemarker.setImageResource(android.R.drawable.star_big_off);

                    } else {
                        setFavoriteOn(newList.get(position).getId());
                        holder.newsfavoritemarker.setImageResource(android.R.drawable.star_big_on);
                    }

                }
            });

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
        TextView newsTitle, newsDescription;

        private GeneralNewsViewHolder(View itemView) {
            super(itemView);
            newspic = itemView.findViewById(R.id.image_news);
            newsfavoritemarker = itemView.findViewById(R.id.news_favorite);
            newsTitle = itemView.findViewById(R.id.title_news);
            newsDescription = itemView.findViewById(R.id.description_news);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public abstract void setFavoriteOn(String id);

    public abstract void setFavoriteOff(String id);


}