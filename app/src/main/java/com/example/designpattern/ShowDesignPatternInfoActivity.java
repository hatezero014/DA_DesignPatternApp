package com.example.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.ContentAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.Content;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShowDesignPatternInfoActivity extends BaseActivity {
    private TextView tv_design_pattern_type;
    private CardView cv_rating, cv_show_code, cv_assignment,
            cv_intent_problem, cv_solution_implementation, cv_techniques_structure, cv_evaluation_application;
    private Button btn_watch_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_design_pattern_info);

        cv_rating = findViewById(R.id.cv_rating);
        cv_show_code = findViewById(R.id.cv_show_code);
        cv_assignment = findViewById(R.id.cv_assignment);

        cv_intent_problem = findViewById(R.id.cv_intent_problem);
        cv_solution_implementation = findViewById(R.id.cv_solution_implementation);
        cv_techniques_structure = findViewById(R.id.cv_techniques_structure);
        cv_evaluation_application = findViewById(R.id.cv_evaluation_application);

        tv_design_pattern_type = findViewById(R.id.tv_design_pattern_type);

        btn_watch_video = findViewById(R.id.btn_watch_video);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String PatternName = (String) bundle.get("PatternName");
        tv_design_pattern_type.setText(PatternName);
        cv_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottomSheetDialog();
            }
        });

        cv_show_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickGoToShowCode(PatternName);
            }
        });

        cv_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickGoToDoAssignment(PatternName);
            }
        });

        cv_intent_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cv_solution_implementation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cv_techniques_structure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cv_evaluation_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_watch_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickGoToWatchVideo(PatternName);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void clickOpenBottomSheetDialog() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet,null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);

        Button btn_cancel = viewDialog.findViewById(R.id.btn_cancel);
        Button btn_send = viewDialog.findViewById(R.id.btn_send);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottomSheetThanksDialog(bottomSheetDialog);
            }
        });
    }

    private void clickOpenBottomSheetThanksDialog(BottomSheetDialog BSD) {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_thanks,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);
        BSD.hide();
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);

        Button btn_undo = viewDialog.findViewById(R.id.btn_undo);

        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void onCLickGoToShowCode(String patternName){
        Intent intent = new Intent(this,ShowCodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onCLickGoToDoAssignment(String patternName) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onCLickGoToWatchVideo(String patternName){
        Intent intent = new Intent(this, PatternsVideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", patternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<Content> getListContent() {
        List<Content> list = new ArrayList<>();

        list.add(new Content(R.drawable.ic_comment,"Intent","Singleton is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance"));
        list.add(new Content(R.drawable.ic_sad_face,"Problem","The Singleton pattern solves two problems at the same time, violating the Single Responsibility Principle"));
        list.add(new Content(R.drawable.icon_happy_face,"Solution","All implementations of the Singleton have these two steps in common"));
        list.add(new Content(R.drawable.ic_car,"Real-World Analogy","The government is an excellent example of the Singleton pattern"));
        list.add(new Content(R.drawable.icon_structure,"Structure","The Singleton class declares the static method getInstance that returns the same instance of its own class"));
        list.add(new Content(R.drawable.ic_light,"Applicability","Use the Singleton pattern when a class in your program should have just a single instance available to all clients; for example, a single database object shared by different parts of the program"));
        list.add(new Content(R.drawable.ic_list,"How to Implement","Add a private static field to the class for storing the singleton instance."));
        list.add(new Content(R.drawable.ic_scale,"Pros and Cons",""));
        list.add(new Content(R.drawable.ic_right_left,"Relations with Other Patterns",""));

        return list;
    }
}