package com.example.servein;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class signIn extends Fragment {

    EditText signInEmail,signInPassword;
    Button signInBtn;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signInEmail = myView.findViewById(R.id.signInEmail);
        signInPassword = myView.findViewById(R.id.signInPassword);
        signInBtn = myView.findViewById(R.id.signInBtn);
        progressBar = myView.findViewById(R.id.progressBar);



        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();

                if(email.length()>0 && password.length()>0)
                {
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://servvvv.000webhostapp.com/app/userlogin.php?e="+email+"&p="+password, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            String result = response.toString();

                            if(result.length()<3)
                            {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Wrong Email or Password!")
                                        .setMessage("Please put all information correctly.")
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();

                            }

                            else
                            {

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userEmail", email);
                                editor.putString("userPassword", password);
                                editor.apply();

                                Intent myintent = new Intent(getActivity(), home.class);
                                startActivity(myintent);

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
                            .setTitle("Empty Field!")
                            .setMessage("Please fill in all the fields.")
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