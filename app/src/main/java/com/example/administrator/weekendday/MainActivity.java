package com.example.administrator.weekendday;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.weekendday.activitys.AtyHeader;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(MainActivity.this, AtyHeader.class);
            startActivity(intent);

            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Message message = mHandler.obtainMessage();
        mHandler.sendEmptyMessageDelayed(1,1000);
    }
}
