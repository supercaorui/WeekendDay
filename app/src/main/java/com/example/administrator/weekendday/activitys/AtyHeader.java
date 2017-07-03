package com.example.administrator.weekendday.activitys;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.weekendday.R;
import com.example.administrator.weekendday.adapter.VgAdapter;
import com.example.administrator.weekendday.fragments.FragmentPerson;
import com.example.administrator.weekendday.fragments.FragmentSearch;
import com.example.administrator.weekendday.fragments.FragmentStar;

import java.util.ArrayList;
import java.util.List;

public class AtyHeader extends AppCompatActivity {

    private ViewPager viewpager;
    private RadioButton rb_star;
    private RadioButton rb_search;
    private RadioButton rb_person;
    private List<Fragment>mList = new ArrayList<>();
    private FragmentStar fg_star;
    private FragmentSearch fg_search;
    private FragmentPerson fg_person;
    private RadioGroup radiogroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty_header);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        View view = getLayoutInflater().inflate(R.layout.action_bar,null,false);
        actionBar.setCustomView(view);
        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.hide();
        radiogroup = (RadioGroup) findViewById(R.id.rp);
        viewpager = (ViewPager) findViewById(R.id.viewpager_main);
        rb_star = (RadioButton) findViewById(R.id.rb_star);
        rb_search = (RadioButton) findViewById(R.id.rb_search);
        rb_person = (RadioButton) findViewById(R.id.rb_person);
        rb_star.setChecked(true);
        initData();
        FragmentManager manager = getSupportFragmentManager();
        VgAdapter adapter = new VgAdapter(manager, mList);
        viewpager.setAdapter(adapter);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_star:
                        View view = getLayoutInflater().inflate(R.layout.action_bar,null,false);
                        actionBar.show();
                        actionBar.setCustomView(view);
                        viewpager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_person:
                        actionBar.hide();
                        viewpager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_search:
                        actionBar.hide();
                        viewpager.setCurrentItem(1,false);
                        break;
                    default:
                        break;
                }
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
//                        View view = getLayoutInflater().inflate(R.layout.action_bar,null,false);
//                        actionBar.setCustomView(view);
                        radiogroup.check(R.id.rb_star);
                        break;
                    case 1:
                        radiogroup.check(R.id.rb_search);
                        break;
                    case 2:
                        radiogroup.check(R.id.rb_person);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
         fg_star =new FragmentStar();
        fg_search = new FragmentSearch();
        fg_person = new FragmentPerson();
        mList.add(fg_star);
        mList.add(fg_search);
        mList.add(fg_person);

    }

}
