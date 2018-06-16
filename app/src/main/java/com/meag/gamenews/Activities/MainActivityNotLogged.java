package com.meag.gamenews.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meag.gamenews.Methods;
import com.meag.gamenews.R;
import com.meag.gamenews.Fragments.Start;

public class MainActivityNotLogged extends AppCompatActivity {
    public int logged = 0;

    SharedPreferences sp;
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
        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String token = sp.getString("token", "");
        //Validating the token to change activities or fragmenss
        if (token.equals("")) {
            logged = 0;
        } else {
            logged = 1;
        }
        switch (logged) {
            case 0:
                Fragment fragment = new Start();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout, fragment).commit();
                break;
            case 1:
                Intent intent = new Intent(this, MainActivityLogged.class);
                startActivity(intent);
                finish();
        }

    }

}