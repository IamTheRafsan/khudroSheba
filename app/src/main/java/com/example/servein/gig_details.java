package com.example.servein;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class gig_details extends AppCompatActivity {

    TextView locationBox, serviceBox, categoryBox, mobileBox, descriptionBox, proName, proEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);

        TextView locationBox = findViewById(R.id.location);
        TextView serviceBox = findViewById(R.id.service);
        TextView categoryBox = findViewById(R.id.category);
        TextView mobileBox = findViewById(R.id.mobile);
        TextView descriptionBox = findViewById(R.id.description);
        TextView nameBox = findViewById(R.id.proName);
        TextView emailBox = findViewById(R.id.proEmail);

        SharedPreferences sharedPreferences2 = gig_details.this.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences2.getString("userEmail", "");
        String  userPassword = sharedPreferences2.getString("userPassword", "");


        String url = "http://servvvv.000webhostapp.com/app/userDetail.php?e="+userEmail+"&p="+userPassword;


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

                        locationBox.setText(thana+", "+district);
                        serviceBox.setText(service);
                        categoryBox.setText(category);
                        mobileBox.setText(mobile);
                        descriptionBox.setText(description);
                        nameBox.setText(name);
                        emailBox.setText(email);


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


        RequestQueue requestQueue = Volley.newRequestQueue(gig_details.this);
        requestQueue.add(jsonArrayRequest);



    }
}