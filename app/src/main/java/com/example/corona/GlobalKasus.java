package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

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

public class GlobalKasus extends AppCompatActivity {
    private RequestQueue dQueue;
    private RecyclerView dRecycle;
    private RecyclerView.Adapter dAdapter;
    private RecyclerView.LayoutManager dLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        final ProgressDialog progressDialog = new ProgressDialog(GlobalKasus.this);
        progressDialog.setMessage("Memuat Data Global");
        progressDialog.show();

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        };

        getSupportActionBar().setTitle("Data Global Covid-19");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dQueue = Volley.newRequestQueue(this);

        dParse();

        handler.sendMessageDelayed(new Message(),1000);

        final Dialog dialog = new Dialog(GlobalKasus.this);
        dialog.setContentView(R.layout.popup);
        dialog.setTitle("Meme");

        ImageView closeX = (ImageView) dialog.findViewById(R.id.closeX);
        /**
         * Jika x diklik, tutup dialog
         */
        closeX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void dParse(){
        String url = "https://api.kawalcorona.com/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<DetailItem> detailItems = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject prov = response.getJSONObject(i);
                        JSONObject att = prov.getJSONObject("attributes");

                        String tvCou = att.getString("Country_Region");
                        String tvConf = att.getString("Confirmed");
                        String tvRec = att.getString("Recovered");
                        String tvDea = att.getString("Deaths");

                        detailItems.add(new DetailItem(tvCou, tvConf, tvRec, tvDea));
                    }

                    dRecycle = findViewById(R.id.dRv_global);
                    dRecycle.setHasFixedSize(true);
                    dLayoutManager = new LinearLayoutManager(getApplicationContext());
                    dAdapter = new GlobalAdapter((detailItems));

                    dRecycle.setLayoutManager(dLayoutManager);
                    dRecycle.setAdapter(dAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        dQueue.add(jsonArrayRequest);
    }

}

