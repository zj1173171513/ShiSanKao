package com.jiyun.asus.shisankao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.mListView)
    ListView mListView;
    private List<Bean> mList = new ArrayList<>();
    private MyAdapter myAdapter;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("请稍后。。。");
        dialog.show();
        initAdapter();
        initData();
        initLiner();
    }

    private void initLiner() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this,OneActivity.class);
                intent.putExtra("list",mList.get(i));
                startActivity(intent);
            }
        });
    }

    private void initData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://172.16.46.25:8080/xiaoshixun.json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Bean>>() {
                }.getType();
                List<Bean> bean = gson.fromJson(string, type);
                mList.addAll(bean);
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();

                        myAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initAdapter() {
        myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(HomeActivity.this, R.layout.item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            Glide.with(HomeActivity.this).load(mList.get(i).getImgs().get(0)).into(viewHolder.mImg);
//            Log.e("TAG",mList.get(i).getImgs().get(0));
            viewHolder.mName.setText(mList.get(i).getName());
            viewHolder.mContent.setText(mList.get(i).getContent());
            return view;
        }

        public  class ViewHolder {
            public View rootView;
            public ImageView mImg;
            public TextView mName;
            public TextView mContent;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.mImg = (ImageView) rootView.findViewById(R.id.mImg);
                this.mName = (TextView) rootView.findViewById(R.id.mName);
                this.mContent = (TextView) rootView.findViewById(R.id.mContent);
            }

        }
    }
}
