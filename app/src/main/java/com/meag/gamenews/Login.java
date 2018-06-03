package com.meag.gamenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Login extends Fragment {
    TextView register_access;
    Button sign_in_button;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.login,container,false);
        findviews(v);
        clicklisteners();
        return v;
    }

    public void findviews(View v){
        register_access=v.findViewById(R.id.register_access);
        sign_in_button=v.findViewById(R.id.sign_in_button);
    }

    public void clicklisteners(){
        register_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new Register();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout,fragment).commit();
            }
        });
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityLogged.class);
                startActivity(intent);
            }
        });


    }

}
