package com.meag.gamenews.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
        //Validating the token to change activities or fragments
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.question);
        builder.setMessage(R.string.exit_question);
        builder.setNegativeButton(android.R.string.no, null);

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                getSupportFragmentManager().popBackStack();
                MainActivityNotLogged.super.onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        dialog.show();
    }

}