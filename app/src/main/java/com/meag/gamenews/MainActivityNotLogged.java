package com.meag.gamenews;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivityNotLogged extends AppCompatActivity {
    public int logged = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch (logged) {
            case 0:
                Fragment fragment = new Register();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout, fragment).commit();
                break;
            case 1:
                Intent intent = new Intent(this, MainActivityLogged.class);
                startActivity(intent);
        }

    }
}
