package com.meag.gamenews.Fragments;


import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {


    private LinearLayout linearLayout;

    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ;

        linearLayout = v.findViewById(R.id.layoutsettings);
        Methods methods = new Methods();
        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
//            Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        return v;
    }

}
