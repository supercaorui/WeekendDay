package com.example.administrator.weekendday.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.weekendday.R;

public class AtyLogin extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewpager;
    private Button bt_not_login;
    private Button bt_weibo;
    private Button bt_weixin;
    private Boolean isFirst =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty_login);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences preferences = getSharedPreferences("mydata", MODE_PRIVATE);
        isFirst = preferences.getBoolean("isfirst", true);
        if (isFirst){
            initView();
            initData();
            preferences.edit().putBoolean("isfirst",false).commit();
        }else {
            Intent intent = new Intent(this, AtyPersonConfig.class);
            startActivity(intent);
        }


    }

    private void initData() {

    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager_login);
        bt_not_login = (Button) findViewById(R.id.bt_not_login);
        bt_weibo = (Button) findViewById(R.id.bt_login_weibo);
        bt_weixin = (Button) findViewById(R.id.bt_login_weixin);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_not_login:
                Intent intent = new Intent(this, AtyPersonConfig.class);
                startActivity(intent);
                break;
            case R.id.bt_login_weibo:

                break;
            case R.id.bt_login_weixin:

                break;
            default:
                break;
        }
        finish();
    }
}
