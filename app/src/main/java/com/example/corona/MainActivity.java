package com.example.corona;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
        private int waktu_loading=4000; //mengatur lama tampilan splash screen

        //4000=4 detik

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_main);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //setelah loading maka akan langsung berpindah ke home activity
                    Intent home=new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(home); //memulai intent
                    finish();

                }
            },waktu_loading);
        }

    }

