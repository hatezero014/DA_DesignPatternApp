package com.example.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.HomePageAdapter;
import com.example.designpattern.Models.HomePage;

import java.util.ArrayList;
import java.util.List;


public class ActivityPhong extends BaseActivity {
    private RecyclerView recyclerView;
    private HomePageAdapter homePageAdapter;
    private TextView tv_design_pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong);

        recyclerView = findViewById(R.id.rcv_homepage);

        tv_design_pattern = findViewById(R.id.tv_design_pattern_type);
        tv_design_pattern.setText("Singleton");

        homePageAdapter = new HomePageAdapter(itemType -> {
            if(itemType.equals("code")){
                onCLickGoToShowCode();
            }
            else if(itemType.equals("Learn more about Singleton")){
                onCLickGoToShowInfo();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        homePageAdapter.setData(getListContent());
        recyclerView.setAdapter(homePageAdapter);

    }

    private List<HomePage> getListContent() {
        List<HomePage> list = new ArrayList<>();

        list.add(new HomePage("Content",1));
        list.add(new HomePage("Learn more about Singleton",2));
        list.add(new HomePage("code",2));
        list.add(new HomePage("Complexity",3));
        list.add(new HomePage("Popularity",3));

        return list;
    }

    private void onCLickGoToShowCode(){
        Intent intent = new Intent(this,ShowCodeActivity.class);
        startActivity(intent);
    }

    private void onCLickGoToShowInfo(){
        Intent intent = new Intent(this,ShowDesignPatternInfoActivity.class);
        startActivity(intent);
    }
}