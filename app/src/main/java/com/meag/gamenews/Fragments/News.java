package com.meag.gamenews.Fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meag.gamenews.Adapters.GeneralNewsAdapter;
import com.meag.gamenews.Database.New;
import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class News extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    GeneralNewsAdapter adapter;
    GridLayoutManager gridLayoutManager;
    ViewModel viewModel;
    SharedPreferences sp;
    private LiveData<List<New>> list;
    SwipeRefreshLayout swipeRefreshLayout;

    public News() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.container, container, false);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        recyclerView = v.findViewById(R.id.recycler_view);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        adapter = new GeneralNewsAdapter(getContext()) {
            @Override
            public void setFavoriteOn(String newid) {
                String token = sp.getString("token", "");
                String userid = sp.getString("userid", "");

                viewModel.PopulateUserInfo("Bearer " + token, sp);
                viewModel.Setfavorite("Bearer " + token, userid, newid);
            }

            @Override
            public void setFavoriteOff(String id) {
                //Aqui tambien...l o l

            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = viewModel.getAllNews();
        list.observe(this, new Observer<List<New>>() {
            @Override
            public void onChanged(@Nullable List<New> news) {
                adapter.setNewList(news);
                adapter.notifyDataSetChanged();
            }
        });

        doInBackground task = new doInBackground();
        task.execute();
        return v;

    }

    @Override
    public void onRefresh() {
        doInBackground task = new doInBackground();
        task.execute();
    }

    public class doInBackground extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            sp = getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
            String token = sp.getString("token", "");
            viewModel.PopulateNews("Bearer " + token);

            return null;
        }

        //For swipe functionality
        @Override
        protected void onPostExecute(Void aVoid) {
            swipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(aVoid);
        }
    }


}
