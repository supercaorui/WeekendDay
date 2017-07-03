package com.example.administrator.weekendday.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.weekendday.adapter.MainListviewAdapter;
import com.example.administrator.weekendday.api.Api;
import com.example.administrator.weekendday.R;
import com.example.administrator.weekendday.adapter.StarHeaderAdapter;
import com.example.administrator.weekendday.bean.StarBean;
import com.example.administrator.weekendday.http.HttpUtils;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/26.
 */

public class FragmentStar extends Fragment {

   // @BindView(R.id.viewpager_header ) ViewPager viewPager;
    ViewPager viewPager;
    //@BindView(R.id.img_you_like)
    private ImageView img_like;
    //@BindView(R.id.img_new_aty)
    private ImageView img_aty;
    //@BindView(R.id.tab_lay)
    //private TabLayout tab_layout;
    //@BindView(R.id.main_pulllistview)
    private PullToRefreshListView refreshListView;
   // @BindView(R.id.dot_layout)
    private LinearLayout dot_lay;
    private FrameLayout frame;
    private RadioGroup rg;
    private RadioButton rb_host,rb_near;

    private StarBean starBean;
    private int page =1;
    private Myadapter myadapter;
    private List<String> vg_list = new ArrayList<>();
    private List<String> imglist = new ArrayList<>();
    private Context context;
    private StarHeaderAdapter vg_adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){

                //myadapter.notifyDataSetChanged();
                Picasso.with(context).load(imglist.get(0)).into(img_like);
                Picasso.with(context).load(imglist.get(1)).into(img_aty);
               //handler.sendEmptyMessageDelayed(2,1000);
            }else if (msg.what==2){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                handler.sendEmptyMessageDelayed(2,1000);
            }else if (msg.what==0){
                myadapter.notifyDataSetChanged();
                //mainListviewAdapter.notifyDataSetChanged();
            }
        }
    };
    private MainListviewAdapter mainListviewAdapter;

    //private  StarBean starBean;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_star,container,false);
        //ButterKnife.bind(context,view);

        initView(view);
        initData();
        initClick();
        //initDot();
        myadapter = new Myadapter();
        viewPager.setAdapter(myadapter);


       // refreshListView.setAdapter(mainListviewAdapter);
        //initListener();
        return view;
    }

    private void initClick() {

    }

    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_header);
        img_like = (ImageView) view.findViewById(R.id.img_you_like);
        img_aty = (ImageView) view.findViewById(R.id.img_new_aty);

        rg = (RadioGroup) view.findViewById(R.id.rpg);
        rb_host = (RadioButton) view.findViewById(R.id.bt_host_aty);
        rb_near = (RadioButton) view.findViewById(R.id.bt_nearest_aty);
        rb_host.setChecked(true);
        rb_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Aty2.class);
                context.startActivity(intent);
            }
        });
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (vg_list.size()!= 0){

                    int currentpage = viewPager.getCurrentItem() % vg_list.size();
                    for (int i = 0; i < dot_lay.getChildCount(); i++) {
                        dot_lay.getChildAt(position).setEnabled(i==currentpage);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化小圆点
    private void initDot() {
        for (int i = 0; i < vg_list.size(); i++) {
            View view = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8,8);
            if(i!=0) {
                params.leftMargin = 5;
            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dots);
            dot_lay.addView(view);
        }
    }

    private void initData() {
        HttpUtils.downLoad(Api.getStarurl(page), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("caorui", "onResponse: "+result);
                Gson gson = new Gson();
                starBean = gson.fromJson(result, StarBean.class);
                Log.d("ccc", "onResponse: "+starBean);
                //mainListviewAdapter = new MainListviewAdapter(context,starBean);
                List<StarBean.ResultBean> result1 =starBean.getResult();
                for (int i = 0; i < result1.size(); i++) {
                    StarBean.ResultBean resultBean = result1.get(i);
                    if (resultBean.getItem_type().equals("event")){
                        Log.d("caorui", "onResponse: "+resultBean.getFront_cover_image_list().get(0));
                        vg_list.add(resultBean.getFront_cover_image_list().get(0));
                        handler.sendEmptyMessage(0);
                    }else if (resultBean.getItem_type().equals("event_list")){
                        imglist.add(resultBean.getFront_cover_image());
                    }
                }
                handler.sendEmptyMessage(1);
                //myadapter.notifyDataSetChanged();
            }
        });

//        vg_adapter = new StarHeaderAdapter(context,vg_list);
//        viewPager.setAdapter(vg_adapter);
    }
    class Myadapter extends PagerAdapter{

        @Override
        public int getCount() {
            Log.d("caorui", "getCount: "+vg_list.size());
            return vg_list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
        //position表示当时页面的下标（根据getcount（）的值来）
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.vg_item,container,false);
            ImageView imageView = (ImageView) view.findViewById(R.id.vg_img);
            Log.d("caorui", "instantiateItem: "+vg_list.size());
            Picasso.with(context).load(vg_list.get(position)).into(imageView);

            container.addView(view);

            return view;
        }
    }
}
