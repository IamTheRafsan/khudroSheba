package com.example.servein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationBar = findViewById(R.id.bottomNavigationBar);


        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new search());
        fragmentTransaction.commit();

        bottomNavigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.menuHome){
                    FragmentManager fManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new search());
                    fragmentTransaction.commit();
                }

                else if(item.getItemId()==R.id.menuCategory)
                {
                    Toast.makeText(home.this, "This page is not available", Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId()==R.id.menuMessages)
                {
                    FragmentManager fManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new messages());
                    fragmentTransaction.commit();
                }

                else if(item.getItemId()==R.id.menuProfile)
                {
                    Intent myIntent = new Intent(home.this, dashboard.class);
                    startActivity(myIntent);
                }

                return true;
            }
        });


    }
}