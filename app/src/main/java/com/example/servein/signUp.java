package com.example.servein;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class signUp extends Fragment {

    EditText signUpName, signUpEmail, signUpPassword,signUpConfirmPassword;
    Button signUpBtn;
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpName = myView.findViewById(R.id.signUpName);
        signUpEmail = myView.findViewById(R.id.signUpEmail);
        signUpPassword = myView.findViewById(R.id.signUpPassword);
        signUpConfirmPassword = myView.findViewById(R.id.signUpConfirmPassword);
        signUpBtn = myView.findViewById(R.id.signUpBtn);
        progressbar = myView.findViewById(R.id.progressbar);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = signUpName.getText().toString();
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                String confirm = signUpConfirmPassword.getText().toString();
                String url = "https://servvvv.000webhostapp.com/app/user.php?n="+name
                        +"&e="+email+"&p="+password;

                if(name.length()>0 && email.length()>0 && password.length()>0)
                {
                    if(signUpPassword.getText().toString().equals(signUpConfirmPassword.getText().toString()))
                    {
                        progressbar.setVisibility(View.VISIBLE);


                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://servvvv.000webhostapp.com/app/regcon.php?e="+email, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                String result = response.toString();

                                if(result.length()>3)
                                {
                                    progressbar.setVisibility(View.GONE);
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Email Already Exists!")
                                            .setMessage("Please put a new email.")
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();

                                }

                                else
                                {

                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });

                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                    requestQueue.add(stringRequest);
                                    progressbar.setVisibility(View.GONE);


                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Congratulations!")
                                            .setMessage("Your Registration Is Completed!")
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        requestQueue.add(jsonArrayRequest);


                    }
                    else
                    {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Passsword did not match!")
                                .setMessage("Please confirm with same password")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                    }
                }
                else
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Empty Field!")
                            .setMessage("Please fill in all the fields")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }



            }
        });




        return myView;
    }
}