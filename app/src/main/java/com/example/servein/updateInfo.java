package com.example.servein;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class updateInfo extends AppCompatActivity {

    EditText fullName, email, password, title, category, mobile, description;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        mobile = findViewById(R.id.mobile);
        description = findViewById(R.id.description);
        updateBtn = findViewById(R.id.updateBtn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameL = fullName.getText().toString();
                String emailL = email.getText().toString();
                String passwordL = password.getText().toString();
                String titleL = title.getText().toString();
                String categoryL = category.getText().toString();
                String mobileL = mobile.getText().toString();
                String descriptionL = description.getText().toString();
                String url = "http://servvvv.000webhostapp.com/app/update.php?n="+nameL+"&e="+emailL+"&p="+passwordL+"&s="+titleL+"&c="+categoryL+"&m="+mobileL+"&d="+descriptionL;




                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(updateInfo.this , "response was sent", Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(updateInfo.this);
                requestQueue.add(request);

            }
        });
    }
}