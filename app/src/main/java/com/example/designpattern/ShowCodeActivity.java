package com.example.designpattern;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.CustomItemDecoration;
import com.example.designpattern.Adapter.SectionAdapter;
import com.example.designpattern.Models.Section;

import java.util.ArrayList;
import java.util.List;

public class ShowCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
//        recyclerView.setLayoutManager(gridLayoutManager);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        CustomItemDecoration itemDecoration = new CustomItemDecoration();
//
//        recyclerView.addItemDecoration(itemDecoration);
//
//        sectionAdapter = new SectionAdapter(this,fragmentManager);
//        sectionAdapter.setData(getListSection());
//        itemDecoration.setHideDividerPositions(0);
//        recyclerView.setAdapter(sectionAdapter);
//
//        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                View child = rv.findChildViewUnder(e.getX(),e.getY());
//                if(child != null){
//                    int position = rv.getChildAdapterPosition(child);
//
//                    if(position == 0){
//                        itemDecoration.setHideDividerPositions(0);
//                    }else if(position == rv.getAdapter().getItemCount()-1){
//                        itemDecoration.setHideDividerPositions(position - 1);
//
//                    }
//                    else itemDecoration.setHideDividerPositions(position - 1, position);
//                }
//                return super.onInterceptTouchEvent(rv, e);
//            }
//        });
    }
    private List<Section> getListSection() {
        List<Section> list = new ArrayList<>();
        list.add(new Section("description"));
        list.add(new Section("pros & cons"));
        list.add(new Section("case"));
        return list;
    }
}