package com.jiyun.asus.shisankao;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private int count = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            count++;
            if (count == 3){
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
            }else {
                handler.postDelayed(runnable,1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.postDelayed(runnable,1000);
    }
}
