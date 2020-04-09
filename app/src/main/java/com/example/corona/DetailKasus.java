package com.example.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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

public class DetailKasus extends AppCompatActivity {
    private RequestQueue dQueue;
    private RecyclerView dRecycle;
    private RecyclerView.Adapter dAdapter;
    private RecyclerView.LayoutManager dLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kasus);

        final ProgressDialog progressDialog = new ProgressDialog(DetailKasus.this);
        progressDialog.setMessage("Memuat Data Provinsi");
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

        getSupportActionBar().setTitle("Data Covid-19 Di Indonesia");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dQueue = Volley.newRequestQueue(this);

        dParse();

        handler.sendMessageDelayed(new Message(),1000);
    }

    private void dParse(){
        String url = "https://api.kawalcorona.com/indonesia/provinsi/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<DetailItem> detailItems = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject prov = response.getJSONObject(i);
                        JSONObject att = prov.getJSONObject("attributes");

                        String tvPro = att.getString("Provinsi");
                        String tvPos = att.getString("Kasus_Posi");
                        String tvSem = att.getString("Kasus_Semb");
                        String tvMen = att.getString("Kasus_Meni");

                        detailItems.add(new DetailItem(tvPro, tvPos, tvSem, tvMen));
                    }

                    dRecycle = findViewById(R.id.dRv_provinsi);
                    dRecycle.setHasFixedSize(true);
                    dLayoutManager = new LinearLayoutManager(getApplicationContext());
                    dAdapter = new DetailAdapter((detailItems));

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