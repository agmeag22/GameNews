package com.meag.gamenews.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meag.gamenews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Games extends Fragment {

    String category;

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
                return TabbedNews.newInstance(category);
            } else if (position == 1) {
                return TabbedPlayers.newInstance(category);
            } else {
                return TabbedPlayers.newInstance(category);
            }
        }

        // This determines the number of tabs
        @Override
        public int getCount() {
            return 3;
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
                case 2:
                    return mContext.getString(R.string.tab_preview);
                default:
                    return null;
            }
        }

    }
}