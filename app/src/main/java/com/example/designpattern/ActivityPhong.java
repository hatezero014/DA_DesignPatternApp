package com.example.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.HomePageAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.HomePage;
import com.example.designpattern.Models.MyUtilities;

import java.util.ArrayList;
import java.util.List;


public class ActivityPhong extends BaseActivity {
    private RecyclerView recyclerView;
    private HomePageAdapter homePageAdapter;
    private TextView tv_design_pattern;
    private String buttonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong);

        recyclerView = findViewById(R.id.rcv_homepage);

        tv_design_pattern = findViewById(R.id.tv_design_pattern_type);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String PatternName = (String) bundle.get("PatternName");
        tv_design_pattern.setText(PatternName);

        buttonName = "Learn more about "+PatternName;

        homePageAdapter = new HomePageAdapter(this, PatternName, new IClickItemListener() {
            @Override
            public void onClickItem(String itemType) {
                if(itemType.equals("code")){
                    onCLickGoToShowCode(PatternName);
                }
                else if(itemType.equals(buttonName)){
                    onCLickGoToShowInfo(PatternName);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        homePageAdapter.setData(getListContent());
        recyclerView.setAdapter(homePageAdapter);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private List<HomePage> getListContent() {
        List<HomePage> list = new ArrayList<>();

        list.add(new HomePage("Content",1));
        list.add(new HomePage(buttonName,2));
        list.add(new HomePage("code",2));
        list.add(new HomePage("Complexity",3));
        list.add(new HomePage("Popularity",3));

        return list;
    }

    private void onCLickGoToShowCode(String patternName){
        Intent intent = new Intent(this,ShowCodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onCLickGoToShowInfo(String patternName){
        Intent intent = new Intent(this,ShowDesignPatternInfoActivity.class);
        startActivity(intent);
    }
}