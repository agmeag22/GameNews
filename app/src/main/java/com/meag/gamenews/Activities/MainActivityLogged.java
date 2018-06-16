package com.meag.gamenews.Activities;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.Fragments.Favorite;
import com.meag.gamenews.Fragments.Games;
import com.meag.gamenews.Fragments.Settings;
import com.meag.gamenews.Menu.MenuModel;
import com.meag.gamenews.Fragments.News;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;
import com.meag.gamenews.Fragments.Start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivityLogged extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private List<MenuModel> headerList = new ArrayList<>();
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private String news_title, games_title, settings_title, favorites_title, logout_title;
    private ViewModel viewModel;
    private List<MenuModel> childModelsList;
    private LiveData<List<String>> listLiveData;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        Methods methods = new Methods();
        if (!methods.isOnline(getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(drawerLayout, R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        if (savedInstanceState == null) {
            Fragment fragment = new News();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.question);
                builder.setMessage(R.string.exit_question);
                builder.setNegativeButton(android.R.string.no, null);

                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        getSupportFragmentManager().popBackStack();
                        MainActivityLogged.super.onBackPressed();
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

            } else {
                getSupportFragmentManager().popBackStackImmediate();
            }

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Function to add any menu option and submenu entries on a expandable list
    private void prepareMenuData() {
        get_menu_titles();
        MenuModel menuModel = new MenuModel(news_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(games_title, true, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        childModelsList = new ArrayList<>();
        listLiveData = viewModel.getCategories();
        listLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                childModelsList.clear();
                for (String c : strings) {
                    MenuModel childModel = new MenuModel(c, false, false);
                    childModelsList.add(childModel);
                }
            }
        });

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
        menuModel = new MenuModel(favorites_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(settings_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(logout_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

    //Setting adapter for the expandable list and making the visuals appear, + adding what would happen when selected
    private void populateExpandableList() {

        expandableListAdapter = new com.meag.gamenews.Menu.ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        //Click listener for parent option
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                get_menu_titles();
                Fragment fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if (headerList.get(groupPosition).menuName.equals(news_title)) {
                            fragment = new News();
                            transaction.replace(R.id.content, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        if (headerList.get(groupPosition).menuName.equals(settings_title)) {
                            fragment = new Settings();
                            transaction.replace(R.id.content, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(favorites_title)) {
                            fragment = new Favorite();
                            transaction.replace(R.id.content, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(logout_title)) {
                            SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                            sp.edit().putString("token", "").apply();
                            fragment = new Start();
                            transaction.replace(R.id.drawer_layout, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }


                        onBackPressed();
                    }
                }
                return false;
            }
        });
        //Click listener for any child option
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Fragment fragment = new Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    String category = childList.get(headerList.get(groupPosition)).get(childPosition).menuName;
                    if (listLiveData.getValue().contains(category)) {
                        fragment = Games.newInstance(category);
                        transaction.replace(R.id.content, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                    }
                    onBackPressed();
                }
                return false;
            }
        });
    }

    //Setting menu option titles
    public void get_menu_titles() {
        news_title = getResources().getString(R.string.news_title);
        games_title = getResources().getString(R.string.games_menu_title);
        settings_title = getResources().getString(R.string.settings_menu_title);
        favorites_title = getResources().getString(R.string.favorites_menu_title);
        logout_title = getResources().getString(R.string.logout);
    }
}

