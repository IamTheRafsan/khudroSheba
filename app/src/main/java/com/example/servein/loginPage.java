package com.example.servein;

import static com.example.servein.R.id.frameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class loginPage extends AppCompatActivity {

    TextView signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, new signIn());
        fragmentTransaction.commit();


        signUpButton.setBackgroundColor(getResources().getColor(R.color.background));
        signUpButton.setTextColor(getResources().getColor(R.color.black));


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new signIn());
                fragmentTransaction.commit();

                signUpButton.setBackgroundColor(getResources().getColor(R.color.background));
                signUpButton.setTextColor(getResources().getColor(R.color.black));

                signInButton.setBackgroundColor(getResources().getColor(R.color.primary));
                signInButton.setTextColor(getResources().getColor(R.color.white));
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new signUp());
                fragmentTransaction.commit();

                signInButton.setBackgroundColor(getResources().getColor(R.color.background));
                signInButton.setTextColor(getResources().getColor(R.color.black));

                signUpButton.setBackgroundColor(getResources().getColor(R.color.primary));
                signUpButton.setTextColor(getResources().getColor(R.color.white));
            }
        });

    }
}