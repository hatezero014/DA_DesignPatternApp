package com.example.designpattern;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designpattern.Adapter.FontAdapter;
import com.example.designpattern.Adapter.SizeAdapter;
import com.example.designpattern.Models.Category;
import com.example.designpattern.Models.Size;
import com.example.designpattern.Share.PDFUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.res.ResourcesCompat;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.Font;


public class ChangFontStype extends AppCompatActivity {
    Spinner spinnerFont;
    Spinner spinnerSize;
    FontAdapter fontAdapter;
    SizeAdapter sizeAdapter;
    TextView textView;
    Button button;

    private CodeView codeView;
    String code;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chang_font_stype);

        spinnerFont = findViewById(R.id.spn_category);
        spinnerSize = findViewById(R.id.spn_size);

        textView = findViewById(R.id.textview1);

        button = findViewById(R.id.button);

        codeView = findViewById(R.id.cv11);

        fontAdapter = new FontAdapter(this,R.layout.item_selected, getListFont());
        sizeAdapter = new SizeAdapter(this, R.layout.item_selected, getListSize());

        spinnerFont.setAdapter(fontAdapter);
        spinnerSize.setAdapter(sizeAdapter);
        spinnerFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = fontAdapter.getItem(position).getName();
                switch (temp) {
                    case "Time New Roman":
                        Typeface typeface = ResourcesCompat.getFont(ChangFontStype.this,R.font.time_new_roman);
                        ChangeFont(typeface);

                        break;
                    case "Arial":
                        Typeface typeface1 = ResourcesCompat.getFont(ChangFontStype.this,R.font.arial);
                        ChangeFont(typeface1);
                        break;
                    case "Pacifico Regular":
                        Typeface typeface2 = ResourcesCompat.getFont(ChangFontStype.this,R.font.pacifico_regular);
                        ChangeFont(typeface2);
                        break;
                    case "Playfair":
                        Typeface typeface3 = ResourcesCompat.getFont(ChangFontStype.this,R.font.playfair);
                        ChangeFont(typeface3);
                        break;
                    case "Poetsen One":
                        Typeface typeface4 = ResourcesCompat.getFont(ChangFontStype.this,R.font.poetsen_one);
                        ChangeFont(typeface4);
                        break;
                    case "Poppins":
                        Typeface typeface5 = ResourcesCompat.getFont(ChangFontStype.this,R.font.poppins);
                        ChangeFont(typeface5);
                        break;
                    case "PT Serif":
                        Typeface typeface6 = ResourcesCompat.getFont(ChangFontStype.this,R.font.pt_serif);
                        ChangeFont(typeface6);
                        break;
                    case "Raleway":
                        Typeface typeface7 = ResourcesCompat.getFont(ChangFontStype.this,R.font.raleway);
                        ChangeFont(typeface7);
                        break;
                    case "Roboto":
                        Typeface typeface8 = ResourcesCompat.getFont(ChangFontStype.this,R.font.roboto);
                        ChangeFont(typeface8);
                        break;
                    case "Roboto Condensed":
                        Typeface typeface9 = ResourcesCompat.getFont(ChangFontStype.this,R.font.roboto_condensed);
                        ChangeFont(typeface9);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int temp = sizeAdapter.getItem(position).getSize();
//                textView.setTextSize(temp);
//                codeView.getOptions().setCode(temp);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFUtil.createPDF(ChangFontStype.this, codeView, "nam_mom.pdf");
            }
        });

        ShowcodeView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void ChangeFont(Typeface typeface) {
        codeView.setCode(code);

        codeView.setOptions(Options.Default.get(this)
                .withLanguage("java")
                .withCode(code)
                .withTheme(ColorTheme.MONOKAI)
                .withFont(typeface));
    }

    private void ShowcodeView() {
        code = "package com.example.designpattern;\n" +
                "\n" +
                "import android.content.Intent;\n" +
                "import android.os.Bundle;\n" +
                "import android.widget.TextView;\n" +
                "\n" +
                "import androidx.appcompat.app.AppCompatActivity;\n" +
                "import androidx.recyclerview.widget.LinearLayoutManager;\n" +
                "import androidx.recyclerview.widget.RecyclerView;\n" +
                "\n" +
                "import com.example.designpattern.Adapter.HomePageAdapter;\n" +
                "import com.example.designpattern.Models.HomePage;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "\n" +
                "public class ActivityPhong extends AppCompatActivity {\n" +
                "    private RecyclerView recyclerView;\n" +
                "    private HomePageAdapter homePageAdapter;\n" +
                "    private TextView tv_design_pattern;\n" +
                "\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_phong);\n" +
                "\n" +
                "        recyclerView = findViewById(R.id.rcv_homepage);\n" +
                "\n" +
                "        tv_design_pattern = findViewById(R.id.tv_design_pattern_type);\n" +
                "        tv_design_pattern.setText(\"Singleton\");\n" +
                "\n" +
                "        homePageAdapter = new HomePageAdapter(itemType -> {\n" +
                "            if(itemType.equals(\"code\")){\n" +
                "                onCLickGoToShowCode();\n" +
                "            }\n" +
                "            else if(itemType.equals(\"Learn more about Singleton\")){\n" +
                "                onCLickGoToShowInfo();\n" +
                "            }\n" +
                "        });\n" +
                "\n" +
                "        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);\n" +
                "        recyclerView.setLayoutManager(linearLayoutManager);\n" +
                "        homePageAdapter.setData(getListContent());\n" +
                "        recyclerView.setAdapter(homePageAdapter);\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    private List<HomePage> getListContent() {\n" +
                "        List<HomePage> list = new ArrayList<>();\n" +
                "\n" +
                "        list.add(new HomePage(\"Content\",1));\n" +
                "        list.add(new HomePage(\"Learn more about Singleton\",2));\n" +
                "        list.add(new HomePage(\"code\",2));\n" +
                "        list.add(new HomePage(\"Complexity\",3));\n" +
                "        list.add(new HomePage(\"Popularity\",3));\n" +
                "\n" +
                "        return list;\n" +
                "    }\n" +
                "\n" +
                "    private void onCLickGoToShowCode(){\n" +
                "        Intent intent = new Intent(this,ShowCodeActivity.class);\n" +
                "        startActivity(intent);\n" +
                "    }\n" +
                "\n" +
                "    private void onCLickGoToShowInfo(){\n" +
                "        Intent intent = new Intent(this,ShowDesignPatternInfoActivity.class);\n" +
                "        startActivity(intent);\n" +
                "    }\n" +
                "}";

        codeView.setCode(code);

        //Typeface typeface = ResourcesCompat.getFont(ChangFontStype.this,R.font.time_new_roman);


        codeView.setOptions(Options.Default.get(this)
                .withLanguage("java")
                .withCode(code)
                .withTheme(ColorTheme.MONOKAI)
                .withFont(Typeface.DEFAULT));

    }

    private List<Size> getListSize() {
        List<Size> list = new ArrayList<>();
        list.add(new Size(22));
        list.add(new Size(24));
        list.add(new Size(26));
        list.add(new Size(30));
        list.add(new Size(34));
        list.add(new Size(40));
        list.add(new Size(48));
        list.add(new Size(56));
        list.add(new Size(72));
        return list;
    }


    private List<Category> getListFont() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(getResources().getString(R.string.arial)));
        list.add(new Category(getResources().getString(R.string.time_new_roman)));
        list.add(new Category(getResources().getString(R.string.pacifico_regular)));
        list.add(new Category(getResources().getString(R.string.playfair)));
        list.add(new Category(getResources().getString(R.string.poetsen_one)));
        list.add(new Category(getResources().getString(R.string.poppins)));
        list.add(new Category(getResources().getString(R.string.pt_serif)));
        list.add(new Category(getResources().getString(R.string.raleway)));
        list.add(new Category(getResources().getString(R.string.roboto)));
        list.add(new Category(getResources().getString(R.string.roboto_condensed)));
        return list;
    }
}