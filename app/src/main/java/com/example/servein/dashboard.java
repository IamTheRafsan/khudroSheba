package com.example.servein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class dashboard extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TextView proEmail;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TabLayout tabLayout = findViewById(R.id.dashboardTab);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        proEmail = findViewById(R.id.proEmail);

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


    }
}