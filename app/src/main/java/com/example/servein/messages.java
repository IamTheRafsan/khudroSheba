package com.example.servein;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class messages extends Fragment {

    ListView listView;
    private ArrayList<HashMap<String, String>> displayList = new ArrayList<>();
    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_messages, container, false);

        listView = myView.findViewById(R.id.listView);
        progressbar = myView.findViewById(R.id.progressbar);

        progressbar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");
        String userPassword = sharedPreferences.getString("userPassword", "");
        String userName = sharedPreferences.getString("userName", "");

        JsonArrayRequest messageJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://servvvv.000webhostapp.com/app/inbox_message.php?e=" + userEmail, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                HashSet<String> uniqueNames = new HashSet<>(); // To store unique names
                for (int x = 0; x < response.length(); x++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String senderName = jsonObject.getString("senderName");
                        String receiverName = jsonObject.getString("receiverName");
                        String sender = jsonObject.getString("sender");
                        String receiver = jsonObject.getString("receiver");


                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
                        String userName = sharedPreferences.getString("userName", "");

                        if (!receiver.equals(userEmail) && !uniqueNames.contains(receiver)) {
                            addNameToDisplayList(receiverName, receiver);
                            uniqueNames.add(receiver);
                        } else if (!sender.equals(userEmail) && !uniqueNames.contains(sender)) {
                            addNameToDisplayList(senderName, sender);
                            uniqueNames.add(sender);
                        }

                        progressbar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                MyAdapter adapter = new MyAdapter(displayList); // Pass displayList to the adapter
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(messageJsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> displayHashMap = displayList.get(position);
                String displayName = displayHashMap.get("displayName");
                String displayRole = displayHashMap.get("displayRole");

                SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("MyAppPreferences2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putString("xemail", displayRole);
                editor.putString("xname", displayName);
                editor.apply();

                Intent myIntent = new Intent(getActivity(), chat.class);
                startActivity(myIntent);

            }
        });


        return myView;
    }


//---------
private void addNameToDisplayList(String name, String role) {
    HashMap<String, String> displayHashMap = new HashMap<>();
    displayHashMap.put("displayName", name);
    displayHashMap.put("displayRole", role);
    displayList.add(displayHashMap);
}
}

//---------
class MyAdapter extends BaseAdapter{

    private ArrayList<HashMap<String, String>> displayList; // Store displayList as a field

    // Constructor to accept displayList as a parameter
    public MyAdapter(ArrayList<HashMap<String, String>> displayList) {
        this.displayList = displayList;
    }

    @Override
    public int getCount() {
        return displayList.size();
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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mView = layoutInflater.inflate(R.layout.inbox, parent, false);

        TextView Dname = mView.findViewById(R.id.Dname);

        HashMap<String, String> displayHashMap = displayList.get(position);
        String displayName = displayHashMap.get("displayName");

        Dname.setText(displayName);

        return mView;
    }
}
