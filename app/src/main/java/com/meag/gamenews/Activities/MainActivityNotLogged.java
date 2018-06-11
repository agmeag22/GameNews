package com.meag.gamenews.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meag.gamenews.R;
import com.meag.gamenews.Fragments.Start;

public class MainActivityNotLogged extends AppCompatActivity {
    public int logged = 0;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }

    }

}