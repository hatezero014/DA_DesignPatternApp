package com.example.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.ContentAdapter;
import com.example.designpattern.Models.Content;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShowDesignPatternInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;
    private FloatingActionButton btnFloating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_design_pattern_info);

        btnFloating = findViewById(R.id.btn_floating_code);

        recyclerView = findViewById(R.id.rcv_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        contentAdapter = new ContentAdapter();
        contentAdapter.setData(getListContent());

        recyclerView.setAdapter(contentAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0)
                    btnFloating.hide();
                else{
                    btnFloating.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ShowCodeActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Content> getListContent() {
        List<Content> list = new ArrayList<>();

        list.add(new Content("Intent","Singleton is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance"));
        list.add(new Content("Problem","The Singleton pattern solves two problems at the same time, violating the Single Responsibility Principle"));
        list.add(new Content("Solution","All implementations of the Singleton have these two steps in common"));
        list.add(new Content("Real-World Analogy","The government is an excellent example of the Singleton pattern"));
        list.add(new Content("Structure","The Singleton class declares the static method getInstance that returns the same instance of its own class"));
        list.add(new Content("Applicability","Use the Singleton pattern when a class in your program should have just a single instance available to all clients; for example, a single database object shared by different parts of the program"));
        list.add(new Content("How to Implement","Add a private static field to the class for storing the singleton instance."));


        return list;
    }
}