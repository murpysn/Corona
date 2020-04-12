package com.example.corona;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class HomeActivity extends AppCompatActivity {
    ViewFlipper v_flipper; //deklarasi flipper/slider image
    TextView tv_positif, tv_sembuh, tv_meninggal, tv_r, tv_positifw, tv_sembuhw, tv_meninggalw;
    CardView cv_pro,cv_glo;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        merubah title action bar
        getSupportActionBar().setTitle("Pantau Covid-19");
//membuat array dengan data dari res drawable dengan file slide1, slide2, slide3, slide4 berupa gambar
        int images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4};
        v_flipper = findViewById(R.id.v_flipper); //inisialisasi bahwa v_flipper = v_flipper pada activity_home
//perulangan untuk memilih images
        for (int i =0; i<images.length; i++){
            fliverImages(images[i]);
        }
        for (int image: images)
            fliverImages(image);
//mengaktifkan atau set running text
        tv_r = (TextView) this.findViewById(R.id.mywidget);
        tv_r.setSelected(true);

        tv_positif = (TextView) findViewById(R.id.tv_positif);
        tv_sembuh = (TextView) findViewById(R.id.tv_sembuh);
        tv_meninggal = (TextView) findViewById(R.id.tv_meninggal);
        tv_positifw = (TextView) findViewById(R.id.tv_positifw);
        tv_sembuhw = (TextView) findViewById(R.id.tv_sembuhw);
        tv_meninggalw = (TextView) findViewById(R.id.tv_meninggalw);
        cv_pro = (CardView) findViewById(R.id.cv_kasus);

        //jika cardview milik indonesia diklik akan mengakses kode dibawah ini
        cv_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mengarahkan ke java class DetailKasus
                Intent DetailKasus = new Intent(getApplicationContext(),DetailKasus.class);
                startActivity(DetailKasus); //memulai activity pada java c;ass DetailKasus
            }
        });

        cv_glo = (CardView) findViewById(R.id.cv_kasusw);
        //akan diakses jika cardview global diklik
        cv_glo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GlobalKasus = new Intent(getApplicationContext(),GlobalKasus.class);
                startActivity(GlobalKasus);
            }
        });
        //inisialisasi volley
        queue = Volley.newRequestQueue(this);
        //menjalankan fungsi/method
        getPositifw();
        getSembuhw();
        getMeninggalw();
        getJSON();
    }

    public  void  fliverImages(int images){
        //membuat objek imageView
        ImageView imageView = new ImageView(this);
        //set nilai dari perulangan yang diatas
        imageView.setBackgroundResource(images);
        //memasukkan nilai kedalam v.flipper
        v_flipper.addView(imageView);
        //set waktu tayang per gambar
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);
        //set arah tayang dari kiri ke kanan
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

    //jika menu pada action bar (logo informasi di klik)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){ //mengambil id dari menu
            case R.id.miAbout: //jika cocok maka mengakses kode dibawah ini
                //membuat objek dialog pada HomeActivity
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.about); //nilai obejk dialog diset dengan layout about
                dialog.setTitle("Author"); //penandaan judul

                Button dialogButton = (Button) dialog.findViewById(R.id.bt_ok);
                /**
                 * Jika tombol diklik, tutup dialog
                 */
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //ini akan membuka layout about
                dialog.show();
                break;
        }
        return true;
    }
    //menampilkan menu pada action bar dari Res menu file menu_main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
//proses parse API JSON
    void getJSON(){
        //set dialog yang muncul saat JSON diproses
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Sedang Memuat Data");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();

        //alamat API
        String url = "https://api.kawalcorona.com/indonesia";
        //proses parse dan memasukkan nilai hasil parse ke TextView yang telah disediakan
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonPost = response.getJSONObject(i);
                        tv_positif.setText(jsonPost.getString("positif"));
                        tv_sembuh.setText(jsonPost.getString("sembuh"));
                        tv_meninggal.setText(jsonPost.getString("meninggal"));
                    }
                    //menutup dialog yang muncul
                    pd.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response",error.toString());
            }
        });
        queue.add(arrayRequest); //menambakan arrayRequest ke antrian
    }

//parse api untuk data positif global
    void getPositifw(){
        String url = "https://api.kawalcorona.com/positif";
        JSONObject jsonObject = new JSONObject();
        final String requestObject = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                        new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonPost = new JSONObject(response.toString());
                    tv_positifw.setText(jsonPost.getString("value"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("Error Response",error.toString());
            }
        });
    queue.add(stringRequest);
    }
//parse api untuk data sembuh global
    void getSembuhw(){
        String url = "https://api.kawalcorona.com/sembuh";
        JSONObject jsonObject = new JSONObject();
        final String requestObject = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonPost = new JSONObject(response.toString());
                            tv_sembuhw.setText(jsonPost.getString("value"));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("Error Response",error.toString());
            }
        });
        queue.add(stringRequest);
    }
    //parse api untuk data menginggal global
void getMeninggalw(){
        String url = "https://api.kawalcorona.com/meninggal";
        JSONObject jsonObject = new JSONObject();
        final String requestObject = jsonObject.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonPost = new JSONObject(response.toString());
                            tv_meninggalw.setText(jsonPost.getString("value"));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("Error Response",error.toString());
            }
        });
        queue.add(stringRequest);
    }

//fungsi method ketika tombol back ditekan
    @Override
    public void onBackPressed() {
        //membuat alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Apakah Anda Ingin Keluar?");
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
