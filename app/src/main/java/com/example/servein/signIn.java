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
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signInEmail = myView.findViewById(R.id.signInEmail);
        signInPassword = myView.findViewById(R.id.signInPassword);
        signInBtn = myView.findViewById(R.id.signInBtn);
        progressbar = myView.findViewById(R.id.progressbar);



        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();

                if(email.length()>0 && password.length()>0)
                {
                    progressbar.setVisibility(View.VISIBLE);
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://servvvv.000webhostapp.com/app/userlogin.php?e="+email+"&p="+password, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            String result = response.toString();

                            if(result.length()<3)
                            {
                                progressbar.setVisibility(View.GONE);

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
                                String url = "http://servvvv.000webhostapp.com/app/userDetail.php?e="+email+"&p="+password;
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {


                                        for (int x=0; x < response.length(); x++)
                                        {
                                            try {
                                                JSONObject jsonObject = response.getJSONObject(x);
                                                String name = jsonObject.getString("name");
                                                String email = jsonObject.getString("email");
                                                String password = jsonObject.getString("password");
                                                String district = jsonObject.getString("district");
                                                String thana = jsonObject.getString("thana");
                                                String service = jsonObject.getString("service");
                                                String category = jsonObject.getString("category");
                                                String mobile = jsonObject.getString("mobile");
                                                String description = jsonObject.getString("description");

                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("userEmail", email);
                                                editor.putString("userPassword", password);
                                                editor.putString("userName", name);
                                                editor.apply();

                                                progressbar.setVisibility(View.GONE);


                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }


                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });


                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                requestQueue.add(jsonArrayRequest);

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
                    progressbar.setVisibility(View.GONE);
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