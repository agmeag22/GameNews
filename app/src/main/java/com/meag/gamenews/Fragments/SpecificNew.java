package com.meag.gamenews.Fragments;


import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.meag.gamenews.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecificNew extends Fragment {
    ImageView image;
    TextView title, body;


    public SpecificNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_specific_new, container, false);
        image = v.findViewById(R.id.specific_new_image);
        title = v.findViewById(R.id.specific_title);
        body = v.findViewById(R.id.body);


        if (getArguments() != null) {
            if (getArguments().getSerializable("coverimage") != null) {
                Glide.with(getContext()).load(getArguments().getSerializable("coverimage"))
                        .apply(RequestOptions.centerCropTransform()).into(image);
            }
            if (true) {
                title.setText(getArguments().getString("description"));
            }
            if (getArguments().getString("body") != null) {
                body.setText(getArguments().getString("body"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    body.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                }
                ;
            }
        }

        return v;

    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        fm.popBackStack();
//    }


}
