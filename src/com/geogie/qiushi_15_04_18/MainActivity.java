package com.geogie.qiushi_15_04_18;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.*;
import com.geogie.qiushi_15_04_18.adapter.MyAdapter;
import com.geogie.qiushi_15_04_18.json.Tool;

import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ListView listView;
    private MyAdapter myAdapter;
    private List<Map<String , Object>>totalList;
    private int page=1;
    private boolean isBottom = false;
    private String path="http://m2.qiushibaike.com/article/list/suggest?page=";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listView);
        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
        new MyAsyncTask().execute(path+page);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isBottom&&scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    new MyAsyncTask().execute(path+(++page));
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom=(firstVisibleItem+visibleItemCount)==totalItemCount;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String medium = list.get(position).get("medium").toString();
//                if (!medium.equals("")){
//                    Intent intent = new Intent();
//                    intent.setClass(MainActivity.this, MediumActivity.class);
//                    startActivity(intent);
//                }
                Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_LONG).show();
            }
        });
    }
    class MyAsyncTask extends AsyncTask<String, Void, List<Map<String ,Object>>>{
        private List<Map<String , Object>>list;
        @Override
        protected List<Map<String, Object>> doInBackground(String... strings) {
            String jsonStr;
            jsonStr = Tool.getJsonStr(strings[0]);
            list = Tool.getJsonList(jsonStr);
            return list;
        }
        @Override
        protected void onPostExecute(List<Map<String, Object>> resul) {
            super.onPostExecute(resul);
            myAdapter.add(resul);
            myAdapter.notifyDataSetChanged();
        }
    }
}
