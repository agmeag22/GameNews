package com.meag.gamenews.Fragments.Login;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meag.gamenews.Activities.MainActivityNotLogged;
import com.meag.gamenews.Database.Repository;
import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;
import com.meag.gamenews.ForAPI.POJOs.Token_API;
import com.meag.gamenews.Activities.MainActivityLogged;
import com.meag.gamenews.ForAPI.POJOs.User_API;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

import java.io.IOException;

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
    private LinearLayout linearLayout;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        findviews(v);
        Methods methods = new Methods();

        if (!methods.isOnline(getActivity().getApplication())) {
            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(android.R.id.content), R.string.snackbar_nointernet, Snackbar.LENGTH_LONG);
            snackbar.show();
//            Toast.makeText(getActivity(),R.string.snackbar_nointernet, Toast.LENGTH_SHORT).show();
        }
        sp = getActivity().getSharedPreferences("com.meag.gamenews", MODE_PRIVATE);
        apiservice = API_Utils.getAPIService(getContext());
        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);
        clicklisteners();
        return v;
    }


    //FIND VIEWS ALL BY ID
    public void findviews(View v) {
        register_access = v.findViewById(R.id.register_access);
        sign_in_button = v.findViewById(R.id.sign_in_button);
        username = v.findViewById(R.id.usernamelogin);
        password = v.findViewById(R.id.userPasswordlogin);
        linearLayout = v.findViewById(R.id.login);
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
                }


            }
        });


    }

    //FUNCTION TO OBTAIN A RESPONSE FROM LOGIN + SAVING THE STATE OF THE TOKEN

    public class doInBackground extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            try {

                if (username != null && password != null) {
                    Token_API token = apiservice.login(username.getText().toString(), password.getText().toString()).execute().body();
                    if (token != null) {
                        sp.edit().putString("username",username.getText().toString()).apply();
                        sp.edit().putString("token", token.getToken()).apply();
                        viewModel.PopulateUserInfo("Bearer " + token.getToken(), sp);
                        Intent intent = new Intent(getContext(), MainActivityLogged.class);
                        startActivity(intent);
                        return R.string.success;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
               return R.string.api_error;
            }

            return R.string.internal_error;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Toast.makeText(getContext(), integer, Toast.LENGTH_SHORT).show();
            super.onPostExecute(integer);
        }
    }


}

