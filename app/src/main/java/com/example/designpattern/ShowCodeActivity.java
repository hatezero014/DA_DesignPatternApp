package com.example.designpattern;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.CodeViewAdapter;
import com.example.designpattern.Adapter.CustomItemDecoration;
import com.example.designpattern.Adapter.ResultAdapter;
import com.example.designpattern.Adapter.SectionAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.CodeLanguage;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.Section;
import com.example.designpattern.Services.PatternService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowCodeActivity extends BaseActivity {
    private RecyclerView rcv_section, rcv_code_result;
    private SectionAdapter sectionAdapter;

    private AutoCompleteTextView act_language;
    private TextView tv_title;

    private CodeViewAdapter codeViewAdapter;
    private ResultAdapter resultAdapter;
    private ImageView img_pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

        rcv_section = findViewById(R.id.rcv_section);

        rcv_code_result = findViewById(R.id.rcv_code_result);

        act_language = findViewById(R.id.act_language);

        tv_title = findViewById(R.id.tv_title);

        img_pattern = findViewById(R.id.img_pattern);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.code));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setLanguageSpinner();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcv_section.setLayoutManager(gridLayoutManager);

        CustomItemDecoration itemDecoration = new CustomItemDecoration();

        rcv_section.addItemDecoration(itemDecoration);

        //Lay ten pattern
        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String PatternName = (String) bundle.get("PatternName");

        tv_title.setText(PatternName);

        sectionAdapter = new SectionAdapter(this, new IClickItemListener() {
            @Override
            public void onClickItem(String itemType) {
                if(itemType.equals("code")){
                    setCodeViewAdapter(PatternName);
                }
                else {
                    setResultAdapter(PatternName);
                }
            }
        });

        if(sectionAdapter.getSelectedPosition() == 0){
            setCodeViewAdapter(PatternName);
        }
        sectionAdapter.setData(getListSection());
        itemDecoration.setHideDividerPositions(0);
        rcv_section.setAdapter(sectionAdapter);

        rcv_section.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                if(child != null){
                    int position = rv.getChildAdapterPosition(child);

                    if(position == 0){
                        itemDecoration.setHideDividerPositions(0);
                    }
                    else itemDecoration.setHideDividerPositions(position - 1, position);
                }
                return super.onInterceptTouchEvent(rv, e);
            }
        });

        setImagePattern(PatternName);
    }

    private void setImagePattern(String patternName) {
        PatternService patternService = new PatternService(this);
        Pattern pattern = patternService.getPatternRow(patternName);

        String image = pattern.getImage();
        int imageResourceId = getResources().getIdentifier(image,"drawable",getPackageName());
        Drawable drawable = getDrawable(imageResourceId);
        img_pattern.setImageDrawable(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void setLanguageSpinner() {
        List<String> aLanguage = Arrays.asList("C#","C++","Go","Java","PHP","Python","Ruby","Rust","Swift","TypeScript");
        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(this,R.layout.item_drop_down,aLanguage);
        act_language.setAdapter(arrayAdapter);

        act_language.setText(aLanguage.get(0),false);
        setOnItemClickListener(act_language);
    }

    private void setOnItemClickListener(AutoCompleteTextView autoCompleteTextView){
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sectionAdapter.getSelectedPosition() == 0)
                    codeViewAdapter.setData(getLanguage());
                else resultAdapter.setData(getLanguage());
            }
        });
    }
    private List<Section> getListSection() {
        List<Section> list = new ArrayList<>();
        list.add(new Section("code"));
        list.add(new Section("result"));
        return list;
    }

    private void setCodeViewAdapter(String patternName){
        codeViewAdapter = new CodeViewAdapter(this, patternName);
        codeViewAdapter.setData(getLanguage());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_code_result.setLayoutManager(linearLayoutManager);
        rcv_code_result.setAdapter(codeViewAdapter);
    }

    private CodeLanguage getLanguage(){
        String language = act_language.getText().toString();
        return new CodeLanguage(language);
    }

    private void setResultAdapter(String patternName){
        resultAdapter = new ResultAdapter(this, patternName);
        resultAdapter.setData(getLanguage());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_code_result.setLayoutManager(linearLayoutManager);
        rcv_code_result.setAdapter(resultAdapter);
    }

    @Override
    protected void onResume() {
            super.onResume();
    }
}