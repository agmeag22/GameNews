package com.meag.gamenews.Fragments;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meag.gamenews.Adapters.GeneralNewsAdapter;
import com.meag.gamenews.Database.New;
import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    GeneralNewsAdapter adapter;
    GridLayoutManager gridLayoutManager;
    ViewModel viewModel;
    SharedPreferences sp;
    private LiveData<List<New>> list;
    SwipeRefreshLayout swipeRefreshLayout;


    public Favorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.container, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        Methods methods = new Methods();
        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
//            Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == 1) {
                    return 2;
                } else {
                    return 2;
                }
            }
        });
        adapter = new GeneralNewsAdapter(getContext()) {
            @Override
            public void setFavoriteOn(String newid) {
                String token = sp.getString("token", "");
                String userid = sp.getString("userid", "");
                viewModel.Setfavorite("Bearer " + token, userid, newid);
            }

            @Override
            public void setFavoriteOff(String newid) {

                String token = sp.getString("token", "");
                String userid = sp.getString("userid", "");
                viewModel.Unsetfavorite("Bearer " + token, userid, newid);

            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = viewModel.getFavoriteNews();
        list.observe(this, new Observer<List<New>>() {
            @Override
            public void onChanged(@Nullable List<New> news) {
                adapter.setNewList(news);
                adapter.notifyDataSetChanged();
            }
        });

        DoInBackground task = new DoInBackground();
        task.execute();
        return v;

    }

    @Override
    public void onRefresh() {
        DoInBackground task = new DoInBackground();
        task.execute();
    }

    public class DoInBackground extends AsyncTask<Void, Void, Void> {
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
