package com.example.designpattern;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.CustomItemDecoration;
import com.example.designpattern.Adapter.HomePageAdapter;
import com.example.designpattern.Adapter.SectionAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.HomePage;
import com.example.designpattern.Models.Section;

import java.util.ArrayList;
import java.util.List;


public class ActivityPhong extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HomePageAdapter homePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong);

        recyclerView = findViewById(R.id.rcv_homepage);

        homePageAdapter = new HomePageAdapter(itemType -> {
            if(itemType.equals("code")){
                onCLickGoToShowCode();
            }
            else if(itemType.equals("Info")){
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
        list.add(new HomePage("Info",2));
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