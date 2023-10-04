package com.example.servein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class chat extends AppCompatActivity {

    RecyclerView chatRecycleView;
    EditText messageTyped;
    Button sendBtn;
    HashMap<String, String> messageHashMap;
    ArrayList<HashMap<String, String>> messageList = new ArrayList<>();
    private String userEmail = "";
    private String xemail = "";
    private myChatAdapter adapter;
    ProgressBar progressbar;
    ProgressBar progressbar2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecycleView = findViewById(R.id.chatRecycleView);
        messageTyped = findViewById(R.id.messageTyped);
        sendBtn = findViewById(R.id.sendBtn);
        progressbar = findViewById(R.id.progressbar);
        progressbar2 = findViewById(R.id.progressbar2);

        progressbar.setVisibility(View.VISIBLE);

        adapter = new myChatAdapter();
        chatRecycleView.setAdapter(adapter);
        chatRecycleView.setLayoutManager(new LinearLayoutManager(chat.this));


        SharedPreferences sharedPreferences = chat.this.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail", "");
        String  userPassword = sharedPreferences.getString("userPassword", "");
        String  userName = sharedPreferences.getString("userName", "");

        SharedPreferences sharedPreferences2 = chat.this.getSharedPreferences("MyAppPreferences2", Context.MODE_PRIVATE);
        String xemail = sharedPreferences2.getString("xemail", "");
        String  xpassword = sharedPreferences2.getString("xpassword", "");
        String  xname = sharedPreferences2.getString("xname", "");


        //--------load message

        messageList.clear();
        JsonArrayRequest messageJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://servvvv.000webhostapp.com/app/chat_load.php?ue="+userEmail+"&xe="+xemail, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int x=0; x < response.length(); x++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(x);
                        String senderName = jsonObject.getString("senderName");
                        String receiverName = jsonObject.getString("receiverName");
                        String sender = jsonObject.getString("sender");
                        String receiver = jsonObject.getString("receiver");
                        String message = jsonObject.getString("message");

                        messageHashMap = new HashMap<>();
                        messageHashMap.put("senderName", senderName);
                        messageHashMap.put("receiverName", receiverName);
                        messageHashMap.put("sender", sender);
                        messageHashMap.put("receiver", receiver);
                        messageHashMap.put("message", message);
                        messageList.add(messageHashMap);

                        progressbar.setVisibility(View.GONE);





                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                adapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(chat.this);
        requestQueue.add(messageJsonArrayRequest);






        //-------------Message sent

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messageList.clear();

                String url = "https://servvvv.000webhostapp.com/app/chat_send.php?sn="+userName+"&rn="+xname+"&s="+userEmail+"&r="+xemail+"&m="+messageTyped.getText().toString();

                if(messageTyped.length() > 0)
                {
                    progressbar2.setVisibility(View.VISIBLE);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressbar2.setVisibility(View.GONE);

                            new AlertDialog.Builder(chat.this)
                                    .setTitle("Message sent successfully!")
                                    .setMessage(" ")
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                            messageTyped.setText(" ");
                            progressbar.setVisibility(View.VISIBLE);
                            //-----reload message
                            messageList.clear();
                            JsonArrayRequest messageJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://servvvv.000webhostapp.com/app/chat_load.php?ue="+userEmail+"&xe="+xemail, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {


                                    for (int x=0; x < response.length(); x++)
                                    {
                                        try {
                                            JSONObject jsonObject = response.getJSONObject(x);
                                            String senderName = jsonObject.getString("senderName");
                                            String receiverName = jsonObject.getString("receiverName");
                                            String sender = jsonObject.getString("sender");
                                            String receiver = jsonObject.getString("receiver");
                                            String message = jsonObject.getString("message");

                                            messageHashMap = new HashMap<>();
                                            messageHashMap.put("senderName", senderName);
                                            messageHashMap.put("receiverName", receiverName);
                                            messageHashMap.put("sender", sender);
                                            messageHashMap.put("receiver", receiver);
                                            messageHashMap.put("message", message);
                                            messageList.add(messageHashMap);


                                            progressbar.setVisibility(View.GONE);


                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    adapter.notifyDataSetChanged();


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });


                            RequestQueue requestQueue = Volley.newRequestQueue(chat.this);
                            requestQueue.add(messageJsonArrayRequest);



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(chat.this.getApplicationContext());
                    requestQueue.add(stringRequest);

                }
                else
                {
                    progressbar2.setVisibility(View.GONE);

                    new AlertDialog.Builder(chat.this)
                            .setTitle("No messages typed")
                            .setMessage("Please type a message")
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

    //------adapter
    private class myChatAdapter extends RecyclerView.Adapter<myChatAdapter.myViewHolder>{

        private class myViewHolder extends RecyclerView.ViewHolder{

            TextView texts;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);

                texts = itemView.findViewById(R.id.texts);
            }
        }

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View myView = inflater.inflate(R.layout.chat, parent, false);

            return new myViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

            if(position < messageList.size())
            {
                HashMap<String, String> messageMap = messageList.get(position);
                String senderName = messageMap.get("senderName");
                String message = messageMap.get("message");
                holder.texts.setText(senderName+": "+message);            }

        }


        @Override
        public int getItemCount() {
            return messageList.size();
        }

    }

}

