package com.meag.gamenews.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.meag.gamenews.Fragments.Login.Login;
import com.meag.gamenews.Fragments.Login.Register;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Start extends Fragment {
    private LinearLayout signup, signin;
    private DrawerLayout drawerLayout;

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
                    .make(getActivity().findViewById(android.R.id.content), R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
//            Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        onclicks();
        return v;
    }

    public void findviews(View v) {
        drawerLayout = v.findViewById(R.id.drawer_layout);
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
                transaction.replace(R.id.appstart, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        //click listener for sign up, and respectve fragment function
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new Register();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.appstart, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
