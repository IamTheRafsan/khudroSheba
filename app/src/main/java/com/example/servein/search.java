package com.example.servein;

import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;


public class search extends Fragment {

    EditText edCategory;
    TextView Dname, Dcategory, Dservice, Ddescription;
    Button searchBtn;
    ListView listView;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_search, container, false);
        edCategory = myView.findViewById(R.id.edCategory);
        searchBtn = myView.findViewById(R.id.searchBtn);
        listView = myView.findViewById(R.id.listView);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());





        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayList = new ArrayList<>();

                loadData();


            }
        });




        return myView;
    }


    //--------------
    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View mView = layoutInflater.inflate(R.layout.gig, null);

            Dname = mView.findViewById(R.id.Dname);
            Dservice = mView.findViewById(R.id.Dservice);
            Dcategory = mView.findViewById(R.id.Dcategory);
            Ddescription = mView.findViewById(R.id.Ddescription);

            hashMap = arrayList.get(position);
            String name = hashMap.get("name");
            String email = hashMap.get("email");
            String service = hashMap.get("service");
            String category = hashMap.get("category");
            String mobile = hashMap.get("mobile");
            String description = hashMap.get("description");

            Dname.setText(name);
            Dservice.setText(service);
            Dcategory.setText(category);
            Ddescription.setText(description);



            return mView;
        }
    }


    //-----------------------
    private void loadData()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://servvvv.000webhostapp.com/app/search.php?c="+edCategory.getText().toString(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int x=0; x<response.length(); x++)
                {

                    try {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String service = jsonObject.getString("service");
                        String category = jsonObject.getString("category");
                        String mobile = jsonObject.getString("mobile");
                        String description = jsonObject.getString("description");

                        hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("email", email);
                        hashMap.put("service", service);
                        hashMap.put("category", category);
                        hashMap.put("mobile", mobile);
                        hashMap.put("description", description);
                        arrayList.add(hashMap);



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }



                }


                if(arrayList.size()>0)
                {
                    MyAdapter myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);
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



}