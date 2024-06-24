package com.example.designpattern;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.PatternInfoVi;
import com.example.designpattern.Models.PatternInformation;
import com.example.designpattern.Services.PatternInfoViService;
import com.example.designpattern.Services.PatternInformationService;

import java.lang.reflect.Field;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorThemeData;
import io.github.kbiakov.codeview.highlight.Font;
import io.github.kbiakov.codeview.highlight.SyntaxColors;

public class ShowInfoActivity extends BaseActivity {
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
            actionBar.setTitle(null);
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

        PatternInfoViService patternInfoViService = new PatternInfoViService(this);
        PatternInfoVi patternInfoVi = patternInfoViService.getInfoVi(patternName);

        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Language", "");

        getInfo(index, patternInformation, patternInfoVi, language);
    }

    private void getInfo(int index, PatternInformation patternInformation, PatternInfoVi patternInfoVi, String language){
        if(index == 1){
            img_section1.setImageResource(R.drawable.ic_comment);
            tv_section1.setText(R.string.intent);
            img_content1.setVisibility(View.GONE);

            img_section2.setImageResource(R.drawable.ic_sad_face);
            tv_section2.setText(R.string.problem);
            img_content2.setVisibility(View.GONE);

            if(language.equals("en")){
                tv_content1.setText(patternInformation.getIntent());
                tv_content2.setText(patternInformation.getProblem());
            }
            else if (language.equals("vi")){
                tv_content1.setText(patternInfoVi.getIntentVi());
                tv_content2.setText(patternInfoVi.getProblemVi());
            }
            else {
                tv_content1.setText(patternInformation.getIntent());
                tv_content2.setText(patternInformation.getProblem());
            }
        } else if (index == 2) {
            img_section1.setImageResource(R.drawable.icon_happy_face);
            tv_section1.setText(R.string.solution);
            img_content1.setVisibility(View.GONE);

            img_section2.setImageResource(R.drawable.ic_list);
            tv_section2.setText(R.string.how_to_implement);
            img_content2.setVisibility(View.GONE);

            if(language.equals("en")){
                tv_content1.setText(patternInformation.getSolution());
                tv_content2.setText(patternInformation.getHowToImplement());
            }
            else if (language.equals("vi")){
                tv_content1.setText(patternInfoVi.getSolutionVi());
                tv_content2.setText(patternInfoVi.getProblemVi());
            }
            else {
                tv_content1.setText(patternInformation.getSolution());
                tv_content2.setText(patternInformation.getHowToImplement());
            }
        } else if (index == 3) {
            img_section1.setImageResource(R.drawable.ic_scale);
            tv_section1.setText(R.string.pros_and_cons);
            img_content1.setVisibility(View.GONE);
            tv_content1.setText(patternInformation.getProsAndCons());

            img_section2.setImageResource(R.drawable.ic_light);
            tv_section2.setText(R.string.applicability);
            img_content2.setVisibility(View.GONE);
            tv_content2.setText(patternInformation.getApplicability());

            if(language.equals("en")){
                tv_content1.setText(patternInformation.getProsAndCons());
                tv_content2.setText(patternInformation.getApplicability());
            }
            else if (language.equals("vi")){
                tv_content1.setText(patternInfoVi.getProsAndConsVi());
                tv_content2.setText(patternInfoVi.getApplicabilityVi());
            }
            else {
                tv_content1.setText(patternInformation.getProsAndCons());
                tv_content2.setText(patternInformation.getApplicability());
            }
        } else{
            img_section1.setImageResource(R.drawable.icon_structure);
            tv_section1.setText(R.string.structure);
            String imgStructure = patternInformation.getStructureImage();
            int imgStructureResourceId = getResources().getIdentifier(imgStructure, "drawable", getPackageName());
            Drawable drawStructure = getDrawable(imgStructureResourceId);
            img_content1.setImageDrawable(drawStructure);

            if (language.equals("en")){
                tv_content1.setText(patternInformation.getStructure());
            }
            else if (language.equals("vi")) {
                tv_content1.setText(patternInfoVi.getStructureVi());
            }
            else tv_content1.setText(patternInformation.getStructure());

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

            ColorThemeData colorTheme = getColorTheme();

            code_view_pseudocode.setOptions(Options.Default.get(this)
                    .withLanguage("java")
                    .withCode(code)
                    .withTheme(colorTheme)
                    .withFont(Font.Consolas));
        }
    }

    private ColorThemeData getColorTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        int displayMode = sharedPreferences.getInt("displayMode",2);

        ColorThemeData customTheme = null;
        if(displayMode == 0){
            SyntaxColors syntaxColors = new SyntaxColors(0x3366CC,
                    0xFF3366,
                    0xFF9933,
                    0x33CC66,
                    0xFF3300,
                    0x000000,
                    0xFF9900,
                    0xFF3333,
                    0x660099,
                    0x33CC33,
                    0x007700
            );
            customTheme = new ColorThemeData(syntaxColors,
                    0x93A1A1,
                    0xfffff,
                    0xfffff,
                    0x657B83);
        } else if (displayMode == 1) {
            SyntaxColors syntaxColors = new SyntaxColors(0xA7E22E,
                    0xFA2772,
                    0x66D9EE,
                    0x76715E,
                    0xE6DB74,
                    0xC1C1C1,
                    0xF8F8F0,
                    0xF92672,
                    0xFA2772,
                    0xA6E22E,
                    0xE6DB74);
            customTheme = new ColorThemeData(syntaxColors,
                    0x48483E,
                    0x000000,
                    0x000000,
                    0xCFD0C2);


        }else {
            SyntaxColors syntaxColors = new SyntaxColors(0x3366CC,
                    0xFF3366,
                    0xFF9933,
                    0x33CC66,
                    0xFF3300,
                    0x000000,
                    0xFF9900,
                    0xFF3333,
                    0x660099,
                    0x33CC33,
                    0x007700
            );
            customTheme = new ColorThemeData(syntaxColors,
                    0x93A1A1,
                    0xfffff,
                    0xfffff,
                    0x657B83);
        }
        return customTheme;
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