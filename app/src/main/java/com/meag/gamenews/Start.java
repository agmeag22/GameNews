package com.meag.gamenews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meag.gamenews.Login.Login;
import com.meag.gamenews.Login.Register;

/**
 * A simple {@link Fragment} subclass.
 */
public class Start extends Fragment {
    LinearLayout signup, signin;

    public Start() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.appstart, container, false);
        findviews(v);
        onclicks();
        return v;
    }

    public void findviews(View v) {
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
