package com.example.designpattern;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.CustomItemDecoration;
import com.example.designpattern.Adapter.SectionAdapter;
import com.example.designpattern.Models.Section;

import java.util.ArrayList;
import java.util.List;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.Font;

public class ShowCodeActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private SectionAdapter sectionAdapter;

    private CodeView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

        recyclerView = findViewById(R.id.rcv_section);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        FragmentManager fragmentManager = getSupportFragmentManager();

        CustomItemDecoration itemDecoration = new CustomItemDecoration();

        recyclerView.addItemDecoration(itemDecoration);

        sectionAdapter = new SectionAdapter(this,fragmentManager);
        sectionAdapter.setData(getListSection());
        itemDecoration.setHideDividerPositions(0);
        recyclerView.setAdapter(sectionAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                if(child != null){
                    int position = rv.getChildAdapterPosition(child);

                    if(position == 0){
                        itemDecoration.setHideDividerPositions(0);
                    }else if(position == rv.getAdapter().getItemCount()-1){
                        itemDecoration.setHideDividerPositions(position - 1);
                    }
                    else itemDecoration.setHideDividerPositions(position - 1, position);
                }
                return super.onInterceptTouchEvent(rv, e);
            }
        });

        codeView = findViewById(R.id.cv);

        String code = "package com.example.designpattern;\n" +
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



        codeView.setOptions(Options.Default.get(this)
                .withLanguage("java")
                .withCode(code)
                .withTheme(ColorTheme.MONOKAI)
                .withFont(Font.Consolas));
    }
    private List<Section> getListSection() {
        List<Section> list = new ArrayList<>();
        list.add(new Section("code"));
        list.add(new Section("task"));
        list.add(new Section("result"));
        return list;
    }
}