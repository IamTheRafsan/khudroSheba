package com.example.servein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dashboard extends AppCompatActivity {

    TextView proName, proEmail;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        proName = findViewById(R.id.proName);
        proEmail = findViewById(R.id.proEmail);
        TabLayout tabLayout = findViewById(R.id.dashboardTab);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);

        sharedPreferences = getSharedPreferences("servIn", Context.MODE_PRIVATE);




        MyAdapter2 MyAdapter2 = new MyAdapter2(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(MyAdapter2);




        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Dashboard");
                    tab.setIcon(R.drawable.dashboard_icon);

                }
                else if(position == 1){
                    tab.setText("Transaction");
                    tab.setIcon(R.drawable.transaction_icon);
                }
                else if(position == 2){
                    tab.setText("Reviews");
                    tab.setIcon(R.drawable.review_icon);
                }

            }
        }).attach();



        //-----------------------------------------dashboard

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");
        String  userPassword = sharedPreferences.getString("userPassword", "");


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


                        proName.setText(name);
                        proEmail.setText(email);



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


        RequestQueue requestQueue = Volley.newRequestQueue(dashboard.this);
        requestQueue.add(jsonArrayRequest);


    }
}