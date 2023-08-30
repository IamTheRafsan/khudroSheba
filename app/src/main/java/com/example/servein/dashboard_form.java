package com.example.servein;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class dashboard_form extends Fragment {

    EditText fullName, email, password, title, category, mobile, description;
    Button updateBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_dashboard_form, container, false);

        fullName = myView.findViewById(R.id.fullName);
        email = myView.findViewById(R.id.email);
        password = myView.findViewById(R.id.password);
        title = myView.findViewById(R.id.title);
        category = myView.findViewById(R.id.category);
        mobile = myView.findViewById(R.id.mobile);
        description = myView.findViewById(R.id.description);
        updateBtn = myView.findViewById(R.id.updateBtn);


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

                        Toast.makeText(getActivity(), "response was sent", Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(request);

            }
        });


        
        return myView;
    }
}