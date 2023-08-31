package com.example.servein;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

public class updateInfo extends AppCompatActivity {

    EditText fullName, email, password, title, mobile, description;
    Spinner categorySpinner, thanaSpinner, districtSpinner;

    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        title = findViewById(R.id.title);
        mobile = findViewById(R.id.mobile);
        description = findViewById(R.id.description);
        updateBtn = findViewById(R.id.updateBtn);
        categorySpinner = findViewById(R.id.categorySpinner);
        districtSpinner = findViewById(R.id.districtSpinner);
        thanaSpinner = findViewById(R.id.thanaSpinner);

        String [] category_items = getResources().getStringArray(R.array.category_items);
        String [] district_items = getResources().getStringArray(R.array.district_items);


        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(updateInfo.this, R.layout.category_list, category_items);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<String> districtAdapter=new ArrayAdapter<String>(updateInfo.this, R.layout.category_list, district_items);
        districtSpinner.setAdapter(districtAdapter);

        String district = districtSpinner.getSelectedItem().toString();

        String [] thana_items_dhaka = getResources().getStringArray(R.array.thana_items_dhaka);
        ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(updateInfo.this, R.layout.category_list, thana_items_dhaka);
        thanaSpinner.setAdapter(thanaAdapter);



        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (districtSpinner.getSelectedItemPosition()==1)
                {

                    String [] thana_items_chittagong = getResources().getStringArray(R.array.thana_items_chittagong);
                    ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(updateInfo.this, R.layout.category_list, thana_items_chittagong);
                    thanaSpinner.setAdapter(thanaAdapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String categoryL = categorySpinner.getSelectedItem().toString();
                String districtL = districtSpinner.getSelectedItem().toString();
                String thanaL = thanaSpinner.getSelectedItem().toString();

                String nameL = fullName.getText().toString();
                String emailL = email.getText().toString();
                String passwordL = password.getText().toString();
                String titleL = title.getText().toString();
                String mobileL = mobile.getText().toString();
                String descriptionL = description.getText().toString();
                String url = "http://servvvv.000webhostapp.com/app/update.php?n="+nameL+"&e="+emailL+"&p="+passwordL+"&di="+districtL+"&th="+thanaL+"&s="+titleL+"&c="+categoryL+"&m="+mobileL+"&d="+descriptionL;


                if(categoryL.length()>0 && districtL.length()>0 && thanaL.length()>0 && nameL.length()>0 && emailL.length()>0 && passwordL.length()>0 && titleL.length()>0 && mobileL.length()>0 && descriptionL.length()>0)
                {
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
                else
                {
                    new AlertDialog.Builder(updateInfo.this)
                            .setTitle("Empty Fields!")
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
    }
}