package com.example.administrator.weekendday.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.weekendday.R;
import com.example.administrator.weekendday.bean.StarBean;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/6/29.
 */

public class MainListviewAdapter extends BaseAdapter {
    private StarBean starBean =new StarBean();
    private Context context;
    private String uri;
    public MainListviewAdapter(Context context,StarBean starBean) {
        this.starBean = starBean;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.d("cccc", "getCount: "+starBean);
        return starBean.getResult_count()-4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StarBean.ResultBean resultBean = starBean.getResult().get(position + 4);
        uri = resultBean.getFront_cover_image_list().get(0);
        View view = convertView;
        MainViewHodlder mainViewHodlder;
        if (view ==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_first_listview,parent,false);
            mainViewHodlder = new MainViewHodlder(view);
        }else {
            mainViewHodlder = (MainViewHodlder) view.getTag();
        }
        Picasso.with(context).load(uri).into(mainViewHodlder.imageView);
        mainViewHodlder.tx_name1.setText(resultBean.getTitle());
        mainViewHodlder.tx_name2.setText(resultBean.getPoi()+"."+Math.floor(resultBean.getDistance()/1000.0)*10/10+"km."+resultBean.getCategory());
        mainViewHodlder.bt_time.setText(resultBean.getTime_info());
        if (resultBean.getCollected_num()==0){

            mainViewHodlder.bt_collect.setText("收藏");

        }else {
            mainViewHodlder.bt_collect.setText(resultBean.getCollected_num()+"人收藏");
        }
        mainViewHodlder.bt_price.setText(resultBean.getPrice_info());
        return view;
    }
    class MainViewHodlder{
        private ImageView imageView;
        private TextView tx_name1,tx_name2;
        private Button bt_time,bt_collect,bt_price;
        public MainViewHodlder(View view){
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
