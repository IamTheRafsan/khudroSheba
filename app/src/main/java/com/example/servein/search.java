package com.example.servein;

import android.net.http.SslCertificate;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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


public class search extends Fragment {

    TextView Dname, Dlocation, Dcategory, Dservice, header;
    Button searchBtn;
    ListView listView;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    AutoCompleteTextView categorySpinner, districtSpinner, thanaSpinner;
    private String sCategory = "";
    private  String sDistrict = "";
    private String sThana = "";




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_search, container, false);

        searchBtn = myView.findViewById(R.id.searchBtn);
        header = myView.findViewById(R.id.header1);
        listView = myView.findViewById(R.id.listView);
        categorySpinner = myView.findViewById(R.id.categorySpinner);
        districtSpinner = myView.findViewById(R.id.districtSpinner);
        thanaSpinner = myView.findViewById(R.id.thanaSpinner);

        String [] category_items = getResources().getStringArray(R.array.category_items);
        String [] district_items = getResources().getStringArray(R.array.district_items);
        String [] thana_items_dhaka = getResources().getStringArray(R.array.thana_items_dhaka);

        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, category_items);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<String> districtAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, district_items);
        districtSpinner.setAdapter(districtAdapter);


        ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, thana_items_dhaka);
        thanaSpinner.setAdapter(thanaAdapter);


        categorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sCategory = (String) parent.getItemAtPosition(position);
            }
        });

        thanaSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sThana = (String) parent.getItemAtPosition(position);
            }
        });


        districtSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 'position' variable contains the selected item's position
                sDistrict = (String) parent.getItemAtPosition(position);
                String selectedItem = (String) parent.getItemAtPosition(position);
                String dis1 = "Dhaka";
                String dis2 = "Chittagong";
                String dis3 = "Rajshahi";
                String dis4 = "Barisal";
                String dis5 = "Sylhet";



                if (selectedItem.equals(dis1))
                {
                    ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, thana_items_dhaka);
                    thanaSpinner.setAdapter(thanaAdapter);

                }
                else if(selectedItem.equals(dis2))
                {
                    String [] thana_items_chittagong = getResources().getStringArray(R.array.thana_items_chittagong);
                    ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, thana_items_chittagong);
                    thanaSpinner.setAdapter(thanaAdapter);
                }
                else if (selectedItem.equals(dis3))
                {
                    ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, thana_items_dhaka);
                    thanaSpinner.setAdapter(thanaAdapter);
                }
                else if (selectedItem.equals(dis4))
                {
                    ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, thana_items_dhaka);
                    thanaSpinner.setAdapter(thanaAdapter);
                }
                else if (selectedItem.equals(dis5))
                {
                    ArrayAdapter<String> thanaAdapter=new ArrayAdapter<String>(getActivity(), R.layout.category_list, thana_items_dhaka);
                    thanaSpinner.setAdapter(thanaAdapter);
                }

            }
        });


        loadData();

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
            Dlocation = mView.findViewById(R.id.Dlocation);

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

            Dname.setText(name);
            Dservice.setText(service);
            Dcategory.setText(category);
            Dlocation.setText(thana+", "+district);


            return mView;
        }
    }



    //-----------------------
    private void loadData()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://servvvv.000webhostapp.com/app/search.php?c="+sCategory+"&di="+sDistrict+"&th="+sThana, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int x=0; x<response.length(); x++)
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



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the ArrayList
                HashMap<String, String> selectedItem = arrayList.get(position);

                // Extract the email from the selected item
                String selectedEmail = selectedItem.get("email");

                // Now, you have the email, and you can use it as needed (e.g., display it in a toast or navigate to a details page).
                Toast.makeText(getActivity(), "Selected Email: " + selectedEmail, Toast.LENGTH_SHORT).show();

                // If you want to navigate to a details page with the email, you can do so here.
                // Example: Start a new Activity or Fragment and pass the email as an extra.
            }
        });


    }



}