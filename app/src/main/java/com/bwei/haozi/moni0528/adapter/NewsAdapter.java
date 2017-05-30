package com.bwei.haozi.moni0528.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwei.haozi.moni0528.R;
import com.bwei.haozi.moni0528.bean.NewsBean;

import java.util.List;

/**
 * Created by haozi on 2017/5/28.
 */

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean.AppBean> list;
    private Context context;

    public NewsAdapter(List<NewsBean.AppBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null){

            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.lv_item, null);

            viewHolder.title = (TextView) convertView.findViewById(R.id.textview);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder)convertView.getTag();

        }
        viewHolder.title.setText(list.get(position).getSecCate());


        return convertView;
    }

    class ViewHolder{

        TextView title;
        CheckBox checkBox;

    }
}
