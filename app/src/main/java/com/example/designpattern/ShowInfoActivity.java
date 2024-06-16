package com.example.designpattern;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.PatternInformation;
import com.example.designpattern.Services.PatternInformationService;

import java.lang.reflect.Field;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.Font;

public class ShowInfoActivity extends AppCompatActivity {
    private TextView tv_section1, tv_section2;
    private TextView tv_content1, tv_content2;
    private ImageView img_section1, img_section2;
    private ImageView img_content1,img_content2;
    private CodeView code_view_pseudocode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initUI();
        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String text = (String) bundle.get("text");

        setContent(text);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void setContent(String text) {
        String[]part = text.split(" ");

        StringBuilder result = new StringBuilder();
        for(int i = 1; i < part.length; i++){
            if(i != part.length - 1){
                result.append(part[i]).append(" ");
            }
            else result.append(part[i]);
        }
        String patternName = result.toString();

        int index = Integer.parseInt(part[0]);

        PatternInformationService patternInformationService = new PatternInformationService(this);
        PatternInformation patternInformation = patternInformationService.getInfo(patternName);

        getInfo(index, patternInformation);
    }

    private void getInfo(int index, PatternInformation patternInformation){
        if(index == 1){
            img_section1.setImageResource(R.drawable.ic_comment);
            tv_section1.setText(R.string.intent);
            img_content1.setVisibility(View.GONE);
            tv_content1.setText(patternInformation.getIntent());

            img_section2.setImageResource(R.drawable.ic_sad_face);
            tv_section2.setText(R.string.problem);
            img_content2.setVisibility(View.GONE);
            tv_content2.setText(patternInformation.getProblem());
        } else if (index == 2) {
            img_section1.setImageResource(R.drawable.icon_happy_face);
            tv_section1.setText(R.string.solution);
            img_content1.setVisibility(View.GONE);
            tv_content1.setText(patternInformation.getSolution());

            img_section2.setImageResource(R.drawable.ic_list);
            tv_section2.setText(R.string.how_to_implement);
            img_content2.setVisibility(View.GONE);
            tv_content2.setText(patternInformation.getHowToImplement());
        } else if (index == 3) {
            img_section1.setImageResource(R.drawable.ic_scale);
            tv_section1.setText(R.string.pros_and_cons);
            img_content1.setVisibility(View.GONE);
            tv_content1.setText(patternInformation.getProsAndCons());

            img_section2.setImageResource(R.drawable.ic_light);
            tv_section2.setText(R.string.applicability);
            img_content2.setVisibility(View.GONE);
            tv_content2.setText(patternInformation.getApplicability());
        } else{
            img_section1.setImageResource(R.drawable.icon_structure);
            tv_section1.setText(R.string.structure);
            String imgStructure = patternInformation.getStructureImage();
            int imgStructureResourceId = getResources().getIdentifier(imgStructure, "drawable", getPackageName());
            Drawable drawStructure = getDrawable(imgStructureResourceId);
            img_content1.setImageDrawable(drawStructure);
            tv_content1.setText(patternInformation.getStructure());

            img_section2.setImageResource(R.drawable.icon_dau_thang);
            tv_section2.setText(R.string.pseudocode);
            String imgPseudocode = patternInformation.getPseudocodeImage();
            int imgPseudocodeResourceId = getResources().getIdentifier(imgPseudocode, "drawable", getPackageName());
            Drawable drawPseudocode = getDrawable(imgPseudocodeResourceId);
            img_content2.setImageDrawable(drawPseudocode);
            tv_content2.setVisibility(View.GONE);
            String code = patternInformation.getPseudocode();
            code_view_pseudocode.setCode(code);

            try {
                Field privateField = CodeView.class.getDeclaredField("vCodeList");
                privateField.setAccessible(true);
                RecyclerView recyclerView = (RecyclerView) privateField.get(code_view_pseudocode);
                recyclerView.setNestedScrollingEnabled(false);
            } catch (NoSuchFieldException  | IllegalAccessException ignored) {

            }

            code_view_pseudocode.setOptions(Options.Default.get(this)
                    .withLanguage("java")
                    .withCode(code)
                    .withTheme(ColorTheme.MONOKAI)
                    .withFont(Font.Consolas));
        }
    }

    private void initUI(){
        tv_section1 = findViewById(R.id.tv_section1);
        tv_section2 = findViewById(R.id.tv_section2);
        img_section1 = findViewById(R.id.img_section1);
        img_section2 = findViewById(R.id.img_section2);
        tv_content1 = findViewById(R.id.tv_content1);
        tv_content2 = findViewById(R.id.tv_content2);
        img_content1 = findViewById(R.id.img_content1);
        img_content2 = findViewById(R.id.img_content2);
        code_view_pseudocode = findViewById(R.id.code_view_pseudocode);
    }
}