package com.jiyun.asus.shisankao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OneActivity extends AppCompatActivity {

    private List<Bean> mList = new ArrayList<>();
    private List<String> listString = new ArrayList<>();
    @Bind(R.id.mRoll)
    RollPagerView mRoll;
    @Bind(R.id.mContents)
    TextView mContents;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);
        initData();
        initRoll();
    }

    private void initRoll() {
        myAdapter = new MyAdapter();
        mRoll.setAdapter(myAdapter);
        mRoll.setPlayDelay(2000);
        mRoll.setAnimationDurtion(2000);
    }

    private void initData() {
        Intent intent = getIntent();
        Bean list = intent.getParcelableExtra("list");
        mList.add(list);
        listString.add(list.getImgs().get(0));
        listString.add(list.getImgs().get(1));
        listString.add(list.getImgs().get(2));
        mContents.setText(list.getContent());
        Toast.makeText(this, ""+mList.get(0).getName(), Toast.LENGTH_SHORT).show();
    }

    private class MyAdapter extends StaticPagerAdapter{
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView = new ImageView(OneActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(OneActivity.this.getApplicationContext()).load(listString.get(0)).into(imageView);
            Glide.with(OneActivity.this.getApplicationContext()).load(listString.get(1)).into(imageView);
            Glide.with(OneActivity.this.getApplicationContext()).load(listString.get(2)).into(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return listString.size();
        }
    }
}
