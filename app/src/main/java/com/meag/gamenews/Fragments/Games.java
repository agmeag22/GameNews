package com.meag.gamenews.Fragments;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Games extends Fragment {

    String category;
    private LinearLayout linearLayout;

    public Games() {
        // Required empty public constructor
    }

    public static Games newInstance(String category) {


        Bundle args = new Bundle();
        args.putString("category", category);
        Games fragment = new Games();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gameinterface, container, false);
        linearLayout = v.findViewById(R.id.gameinterface);
        Methods methods = new Methods();
        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
//            Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        category = getArguments() != null ? getArguments().getString("category") : "";

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        PagerGameAdapter adapter = new PagerGameAdapter(getContext(), getChildFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    public class PagerGameAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public PagerGameAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return TabbedNew.newInstance(category);
            } else {
                return TabbedPlayers.newInstance(category);
            }
        }

        // This determines the number of tabs
        @Override
        public int getCount() {
            return 2;
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return mContext.getString(R.string.tab_news);
                case 1:
                    return mContext.getString(R.string.tab_top_players);
                default:
                    return null;
            }
        }

    }
}