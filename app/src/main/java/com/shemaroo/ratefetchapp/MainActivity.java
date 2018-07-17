package com.shemaroo.ratefetchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shemaroo.ratefetchapp.adapter.DataAdapter;
import com.shemaroo.ratefetchapp.datepicker.DatePickerFrom;
import com.shemaroo.ratefetchapp.datepicker.DatePickerTo;
import com.shemaroo.ratefetchapp.model.RecentList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://192.168.2.128/yedaz/check_rate.php";
    private Button submit;
    private EditText dateFrom,dateTo;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;


    public static final String JSON_ARRAY = "result";
    private JSONArray result;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    List<RecentList> fullData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        submit     = (Button)findViewById(R.id.btn_submit);
        dateFrom   = (EditText)findViewById(R.id.date_from);
        dateTo     = (EditText)findViewById(R.id.date_to);

        fullData = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecent);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFrom();
                FragmentManager fm = getSupportFragmentManager();
                newFragment.show(fm,"Date Picker");
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogFragment otherFragment  = new DatePickerTo();
                FragmentManager fmm = getSupportFragmentManager();
                otherFragment.show(fmm,"Date Picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dtFrom = dateFrom.getText().toString().trim();
                String dtTo   = dateTo.getText().toString().trim();
                fetchData(dtFrom,dtTo);
            }
        });



    }


    private void fetchData(final String dtFrom,final String dtTo) {

        // Tag used to cancel the request
        String tag_string_req = "req_signup";
        progressDialog.setMessage("Fetching data pls wait...");
        progressDialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Data Response: " + response.toString());

                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(response);


                    result = jObj.getJSONArray(JSON_ARRAY);

                    //Calling method getStudents to get the students from the JSON Array
                    getRecentActivity(result);


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                  toast("Json error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                toast("Unknown Error occurred");
                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("dateFrom", dtFrom);
                params.put("dateTo", dtTo);

                return params;
            }

        };

        // Adding request to request queue
        AndroidLoginController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void getRecentActivity(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {

            RecentList recentList = new RecentList();

            JSONObject json = null;
            try {


                json = j.getJSONObject(i);

                recentList.setId(json.getString("id"));
                recentList.setTradeDate(json.getString("trade_date"));
                recentList.setUsd(json.getString("usd"));
                recentList.setGbp(json.getString("gbp"));
                recentList.setEuro(json.getString("euro"));
                recentList.setYen(json.getString("yen"));

                progressDialog.hide();



            } catch (JSONException e) {

                e.printStackTrace();
            }

            fullData.add(recentList);
        }

        adapter = new DataAdapter(fullData, this);

        recyclerView.setAdapter(adapter);
    }

    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}
