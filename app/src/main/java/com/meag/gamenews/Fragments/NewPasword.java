package com.meag.gamenews.Fragments;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meag.gamenews.Database.Repository;
import com.meag.gamenews.Database.ViewModel;
import com.meag.gamenews.ForAPI.APIService;
import com.meag.gamenews.ForAPI.API_Utils;
import com.meag.gamenews.ForAPI.POJOs.User_API;
import com.meag.gamenews.Fragments.Login.Login;
import com.meag.gamenews.Methods;
import com.meag.gamenews.R;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class NewPasword extends Fragment {
    private TextView register_access;
    private Button save_button;
    private EditText oldpassword, password;
    private SharedPreferences sp;
    private APIService apiservice;
    private Repository repository;
    private Application application;
    private ViewModel viewModel;
    private Login.doInBackground login;
    private LinearLayout linearLayout;
    private TextView confirmPassword;

    public NewPasword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_pasword, container, false);
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
        //register_access = v.findViewById(R.id.register_access);
        save_button = v.findViewById(R.id.save_password);
        oldpassword = v.findViewById(R.id.current_password);
        password = v.findViewById(R.id.new_password);
        confirmPassword = v.findViewById(R.id.confirm_new_password);
        linearLayout = v.findViewById(R.id.login);
    }

    //CLICK LISTENERS FOR BUTTONS
    public void clicklisteners() {
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldpassword.getText() != null & !oldpassword.getText().toString().equals("")) {
                    if (password.getText() != null && !password.getText().toString().equals("")) {
                        if (confirmPassword.getText() != null && !confirmPassword.getText().toString().equals("")) {
                            if (confirmPassword.getText().toString().equals(password.getText().toString())) {
                                doInBackground task = new doInBackground(oldpassword.getText().toString(), password.getText().toString());
                                task.execute();
                            } else {
                                confirmPassword.setError(getString(R.string.password_notmatch));
                            }
                        } else {
                            confirmPassword.setError(getString(R.string.is_empty));
                        }
                    } else {
                        password.setError(getString(R.string.is_empty));
                    }
                } else {
                    oldpassword.setError(getString(R.string.is_empty));
                }
            }
        });


    }

    //FUNCTION TO OBTAIN A RESPONSE FROM LOGIN + SAVING THE STATE OF THE TOKEN

    public class doInBackground extends AsyncTask<Void, Void, Integer> {

        private String password, newpassword;

        public doInBackground(String password, String newpassword) {
            this.password = password;
            this.newpassword = newpassword;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {

                if (oldpassword != null && password != null) {
                    String token = sp.getString("token", "");
                    User_API user = apiservice.getUserDetails("Bearer " + token).execute().body();
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            User_API m = apiservice.editUser("Bearer "+token, user.getId(), newpassword).execute().body();
                            if (m != null) {
                                return R.string.password_updated;
                            } else {
                                return R.string.api_error;
                            }
                        } else {
                            return R.string.password_not_match;
                        }
                    } return R.string.api_error;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return R.string.error;
            }
            return R.string.error;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Toast.makeText(getContext(), integer, Toast.LENGTH_SHORT).show();
            super.onPostExecute(integer);
        }
    }


}
