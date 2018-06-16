package com.meag.gamenews.Fragments;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meag.gamenews.Adapters.PlayerAdapter;
import com.meag.gamenews.Database.Player;
import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabbedPlayers extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    PlayerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ViewModel viewModel;
    SharedPreferences sp;
    private LiveData<List<Player>> list;
    SwipeRefreshLayout swipeRefreshLayout;
    String category;
    private SwipeRefreshLayout swipeRefreshLayout1;

    public TabbedPlayers() {
        // Required empty public constructor
    }

    public static TabbedPlayers newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);
        TabbedPlayers fragment = new TabbedPlayers();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.container, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        Methods methods = new Methods();
        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
//            Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        category = getArguments() != null ? getArguments().getString("category") : "";
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        recyclerView = v.findViewById(R.id.recycler_view);

        swipeRefreshLayout.setOnRefreshListener(this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new PlayerAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = viewModel.getPlayersByCategory(category);
        list.observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(@Nullable List<Player> players) {
                adapter.setPlayerList(players);
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
            viewModel.PopulatePlayers("Bearer " + token);

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
