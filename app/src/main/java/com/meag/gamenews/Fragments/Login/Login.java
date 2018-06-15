package com.meag.gamenews.Fragments.Login;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meag.gamenews.Database.Repository;
import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;
import com.meag.gamenews.ForAPI.POJOs.Token_API;
import com.meag.gamenews.Activities.MainActivityLogged;
import com.meag.gamenews.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Login extends Fragment {
    private TextView register_access;
    private Button sign_in_button;
    private EditText username, password;
    private SharedPreferences sp;
    private APIService apiservice;
    private Repository repository;
    private Application application;
    private ViewModel viewModel;
    private doInBackground login;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        sp = getActivity().getSharedPreferences("com.meag.gamenews", MODE_PRIVATE);
        apiservice = API_Utils.getAPIService(getContext());
        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);
        findviews(v);
        clicklisteners();
        return v;
    }

    //FIND VIEWS ALL BY ID
    public void findviews(View v) {
        register_access = v.findViewById(R.id.register_access);
        sign_in_button = v.findViewById(R.id.sign_in_button);
        username = v.findViewById(R.id.usernamelogin);
        password = v.findViewById(R.id.userPasswordlogin);
    }

    //CLICK LISTENERS FOR BUTTONS
    public void clicklisteners() {
        register_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Register();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout, fragment).commit();
            }
        });
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null && password.getText() != null) {
                    login = new doInBackground();
                    login.execute();
//                    logIn(username.getText().toString(), password.getText().toString());
                }


            }
        });


    }

    //FUNCTION TO OBTAIN A RESPONSE FROM LOGIN + SAVING THE STATE OF THE TOKEN

    public class doInBackground extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                if (username != null && password != null) {
                    Token_API token = apiservice.login(username.toString(), password.toString()).execute().body();
                    if (token != null) {
                        sp.edit().putString("token", token.getToken()).apply();
                        viewModel.PopulateUserInfo(token.getToken(), sp);
                        Intent intent = new Intent(getContext(), MainActivityLogged.class);
                        startActivity(intent);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    }
}

