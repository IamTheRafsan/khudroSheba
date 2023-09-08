package com.example.servein;

import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;

public class messages extends Fragment {

    RecyclerView recyclerView;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_messages, container, false);



        recyclerView = myView.findViewById(R.id.inboxRecycleView);


        myAdapter adapter = new myAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://servvvv.000webhostapp.com/app/inbox_name.php?e=sending.to.evan@gmail.com", null, new Response.Listener<JSONArray>() {
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
                        //String sender = jsonObject.getString("sender");
                        //String receiver = jsonObject.getString("receiver");
                        //String message = jsonObject.getString("message");

                        hashMap = new HashMap<>();
                        hashMap.put("name", name);
                        hashMap.put("email", email);
                        hashMap.put("password", password);
                        hashMap.put("district", district);
                        hashMap.put("thana", thana);
                        hashMap.put("service", service);
                        hashMap.put("category", category);
                        hashMap.put("mobile", mobile);
                        hashMap.put("description", description);
                        //hashMap.put("sender", sender);
                        //hashMap.put("receiver", receiver);
                        //hashMap.put("message", message);
                        arrayList.add(hashMap);

                        Toast.makeText(getActivity(), "Working ", Toast.LENGTH_SHORT).show();




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

        return myView;
    }

    //-------------------------recycle view adapter

    private class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

        private class myViewHolder extends RecyclerView.ViewHolder {

            TextView Dname, Dmessage;


            public myViewHolder(@NonNull View itemView) {
                super(itemView);

                Dname = itemView.findViewById(R.id.Dname);
                Dmessage = itemView.findViewById(R.id.Dmessage);


            }
        }

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View recycleView = inflater.inflate(R.layout.inbox, parent, false);

            return new myViewHolder(recycleView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


            hashMap = arrayList.get(position);
            String name = hashMap.get("name");
            String email = hashMap.get("email");
            String password = hashMap.get("password");
            String district = hashMap.get("district");
            String thana = hashMap.get("thana");
            String service = hashMap.get("service");
            String category = hashMap.get("category");
            String mobile = hashMap.get("mobile");
            String description = hashMap.get("description");
            //String sender = hashMap.get("sender");
            //String receiver = hashMap.get("receiver");
            //String message = hashMap.get("message");

            holder.Dname.setText(name);





        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

    }
}
