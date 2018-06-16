package com.meag.gamenews.Fragments;


import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.meag.gamenews.Fragments.Login.Login;
import com.meag.gamenews.Fragments.Login.Register;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Start extends Fragment {
    LinearLayout signup, signin;
    private RelativeLayout relativeLayout;

    public Start() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.appstart, container, false);
        findviews(v);
        Methods methods = new Methods();
        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
            //Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        onclicks();
        return v;
    }

    public void findviews(View v) {
        relativeLayout = v.findViewById(R.id.appstart);
        signin = v.findViewById(R.id.signin_option);
        signup = v.findViewById(R.id.signup_option);
    }

    //Click listeners
    public void onclicks() {
        //click listener for sign in button, and fragment oppening action
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Login();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.appstart, fragment).commit();
            }
        });
        //click listener for sign up, and respectve fragment function
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Register();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.appstart, fragment).commit();
            }
        });
    }
}
