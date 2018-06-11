package com.meag.gamenews.Fragments.Login;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;
import com.meag.gamenews.ForAPI.Token_API;
import com.meag.gamenews.Activities.MainActivityLogged;
import com.meag.gamenews.R;

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

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        sp = getActivity().getSharedPreferences("com.meag.gamenews", MODE_PRIVATE);
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
                    logIn(username.getText().toString(), password.getText().toString());
                }


            }
        });


    }

    //FUNCTION TO OBTAIN A RESPONSE FROM LOGIN + SAVING THE STATE OF THE TOKEN
    public void logIn(String user, String password) {
        apiservice = API_Utils.getAPIService();

        apiservice.login(user, password).enqueue(new Callback<Token_API>() {
            @Override
            public void onResponse(Call<Token_API> call, Response<Token_API> response) {
                if (response.isSuccessful()) {
                    showResponse(response.toString());
                    showResponse(response.body().getToken());
                    if (response.body() != null && response.body().getToken() != null) {
                        sp.edit().putString("token", response.body().getToken()).apply();
                        Intent intent = new Intent(getContext(), MainActivityLogged.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Token_API> call, Throwable t) {

            }
        });
    }

    public void showResponse(String response) {
        Log.d("MAIN_RESPONSE", "showResponse: " + response);
    }

}
