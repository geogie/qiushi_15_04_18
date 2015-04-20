package com.geogie.qiushi_15_04_18.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.geogie.qiushi_15_04_18.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Created with Inte[i] IDEA.
 * User: renchaojun[FR]
 * Date:@date ${date}
 * Email:1063658094@qq.com
 */
public  class MyAdapter extends BaseAdapter {

    private List<Map<String, Object>> mylist;
    private Context context;
    private LayoutInflater inflater;
    public MyAdapter(Context context){
        this.context = context;
        this.mylist = new ArrayList<Map<String, Object>>();
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return mylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void add(List<Map<String, Object>> list){
        mylist.addAll(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= inflater.inflate(R.layout.myview, null);
            viewHolder=new ViewHolder();
            viewHolder.textView_comments_count = (TextView) convertView.findViewById(R.id.textView_comments_count);
            viewHolder.textView_login = (TextView) convertView.findViewById(R.id.textView_login);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView=(TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageBitmap((Bitmap)(mylist.get(position).get("image")));
        viewHolder.textView.setText(mylist.get(position).get("content").toString());
        viewHolder.textView_comments_count.setText("点击量："+mylist.get(position).
                get("comments_count").toString());
        viewHolder.textView_login.setText("作者："+mylist.get(position).get("login").toString());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView, textView_login,textView_comments_count;
    }
}
