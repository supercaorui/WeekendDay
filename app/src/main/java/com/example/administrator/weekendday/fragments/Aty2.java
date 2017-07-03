package com.example.administrator.weekendday.fragments;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.weekendday.MainActivity;
import com.example.administrator.weekendday.R;
import com.example.administrator.weekendday.activitys.InfoAty;
import com.example.administrator.weekendday.adapter.MainListviewAdapter;
import com.example.administrator.weekendday.api.Api;
import com.example.administrator.weekendday.bean.StarBean;
import com.example.administrator.weekendday.http.HttpUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Aty2 extends AppCompatActivity {
    StarBean starBean;
    private List<StarBean.ResultBean> mlist = new ArrayList<>();
    private int page = 1;

    MAda mAda;
    private PullToRefreshListView refreshListView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAda.notifyDataSetChanged();
            refreshListView.onRefreshComplete();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty2);
        refreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        Log.d("androidxxx", "onCreate: "+1);
        initData(page);
        mAda= new MAda();
        refreshListView.setAdapter(mAda);

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新"+label);
                initData(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("请稍后");
                page++;
                initData(page);
                Log.d("nihao", "onPullUpToRefresh: "+page);
            }
        });

    }
    private MainListviewAdapter mainListviewAdapter;
    private void initData(int page) {
        Log.d("androidxxx", "onCreate: "+2);
        final int count = page;
        HttpUtils.downLoad(Api.getStarurl(page), new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("androidxxx", "onCreate: "+3);
                String result = response.body().string();
                Log.d("androidxxx", "onCreate: "+result);
                Gson gson = new Gson();
                StarBean starBean1 = gson.fromJson(result, StarBean.class);
                if (count==1){
                    mlist.clear();
                    mlist.addAll(starBean1.getResult());
                    mlist.remove(0);
                    mlist.remove(0);
                    mlist.remove(0);
                    mlist.remove(0);
                }else {
                    mlist.addAll(starBean1.getResult());
                }
                //starBean = starBean1;
                //mainListviewAdapter = new MainListviewAdapter(Aty2.this,starBean);
                handler.sendEmptyMessage(1);
            }
        });
    }

    public class MAda extends BaseAdapter{

        @Override
        public int getCount() {
            Log.d("androidxxx", "getCount: "+(mlist.size()));
            return (mlist.size());
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           // StarBean starBean2 = mlist.get(page-1);
            StarBean.ResultBean resultBean = mlist.get(position);
            String uri = resultBean.getFront_cover_image_list().get(0);
            View view = convertView;
            MViewHodlder mainViewHodlder;
            if (view ==null){
                view = LayoutInflater.from(Aty2.this).inflate(R.layout.item_first_listview,parent,false);
                mainViewHodlder = new MViewHodlder(view);
            }else {
                mainViewHodlder = (MViewHodlder) view.getTag();
            }
            Log.d("androidxxx", "img: "+uri);
            Log.d("androidxxx", "title: "+resultBean.getTitle());
            Log.d("androidxxx", "dis: "+resultBean.getDistance());
            Log.d("androidxxx", "time: "+resultBean.getTime_info());
            Picasso.with(Aty2.this).load(uri).into(mainViewHodlder.imageView);
            mainViewHodlder.tx_name1.setText(resultBean.getTitle());
            mainViewHodlder.tx_name2.setText(resultBean.getPoi()+"."+Math.floor(resultBean.getDistance()/1000.0)*10/10+"km."+resultBean.getCategory());
            mainViewHodlder.bt_time.setText(resultBean.getTime_info());
            if (resultBean.getCollected_num()==0){

                mainViewHodlder.bt_collect.setText("收藏");

            }else {
                mainViewHodlder.bt_collect.setText(resultBean.getCollected_num()+"人收藏");
            }
            mainViewHodlder.bt_price.setText(resultBean.getPrice_info());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Aty2.this, InfoAty.class);
                    Aty2.this.startActivity(intent);
                }
            });
            return view;
        }
    }
    class MViewHodlder{
        private ImageView imageView;
        private TextView tx_name1,tx_name2;
        private Button bt_time,bt_collect,bt_price;
        public MViewHodlder(View view){
            imageView = (ImageView) view.findViewById(R.id.first_item_img);
            tx_name1 = (TextView) view.findViewById(R.id.first_item_name);
            tx_name2 = (TextView) view.findViewById(R.id.first_item_detail);
            bt_time = (Button) view.findViewById(R.id.first_item_deadline);
            bt_collect = (Button) view.findViewById(R.id.first_item_collect);
            bt_price = (Button) view.findViewById(R.id.first_item_price);
            view.setTag(this);
        }
    }
}
