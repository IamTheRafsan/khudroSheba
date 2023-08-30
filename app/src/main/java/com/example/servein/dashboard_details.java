package com.example.servein;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dashboard_details extends Fragment {

    TextView serviceBox, categoryBox, mobileBox, descriptionBox;
    Button updateFragment;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_dashboard_details, container, false);

        serviceBox = myView.findViewById(R.id.service);
        categoryBox = myView.findViewById(R.id.category);
        mobileBox = myView.findViewById(R.id.mobile);
        descriptionBox = myView.findViewById(R.id.description);
        updateFragment = myView.findViewById(R.id.updateFragment);

        String email = "sending.to.evan@gmail.com";
        String password = "evan123";

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
                        String service = jsonObject.getString("service");
                        String category = jsonObject.getString("category");
                        String mobile = jsonObject.getString("mobile");
                        String description = jsonObject.getString("description");

                        serviceBox.setText(service);
                        categoryBox.setText(category);
                        mobileBox.setText(mobile);
                        descriptionBox.setText(description);


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


        updateFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent = new Intent(getActivity(), updateInfo.class);
                startActivity(myintent);


            }
        });


        return myView;
    }

}