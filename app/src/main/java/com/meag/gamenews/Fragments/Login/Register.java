package com.meag.gamenews.Fragments.Login;

import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meag.gamenews.Methods;
import com.meag.gamenews.R;


public class Register extends Fragment {
    TextView sign_in;
    LinearLayout linearLayout;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register, container, false);
        linearLayout = v.findViewById(R.id.layoutregister);
        Methods methods = new Methods();
        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(linearLayout, R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
            //Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        sign_in = v.findViewById(R.id.sign_in_access);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Login();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout, fragment).commit();
            }
        });
        return v;
    }

}
