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


public class ChangFontStype extends AppCompatActivity {
    Spinner spinnerFont;
    Spinner spinnerSize;
    FontAdapter fontAdapter;
    SizeAdapter sizeAdapter;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chang_font_stype);

        spinnerFont = findViewById(R.id.spn_category);
        spinnerSize = findViewById(R.id.spn_size);

        textView = findViewById(R.id.textview1);

        button = findViewById(R.id.button);

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
                        textView.setTypeface(typeface);

                        break;
                    case "Arial":
                        Typeface typeface1 = ResourcesCompat.getFont(ChangFontStype.this,R.font.arial);
                        textView.setTypeface(typeface1);
                        break;
                    case "Pacifico Regular":
                        Typeface typeface2 = ResourcesCompat.getFont(ChangFontStype.this,R.font.pacifico_regular);
                        textView.setTypeface(typeface2);
                        break;
                    case "Playfair":
                        Typeface typeface3 = ResourcesCompat.getFont(ChangFontStype.this,R.font.playfair);
                        textView.setTypeface(typeface3);
                        break;
                    case "Poetsen One":
                        Typeface typeface4 = ResourcesCompat.getFont(ChangFontStype.this,R.font.poetsen_one);
                        textView.setTypeface(typeface4);
                        break;
                    case "Poppins":
                        Typeface typeface5 = ResourcesCompat.getFont(ChangFontStype.this,R.font.poppins);
                        textView.setTypeface(typeface5);
                        break;
                    case "PT Serif":
                        Typeface typeface6 = ResourcesCompat.getFont(ChangFontStype.this,R.font.pt_serif);
                        textView.setTypeface(typeface6);
                        break;
                    case "Raleway":
                        Typeface typeface7 = ResourcesCompat.getFont(ChangFontStype.this,R.font.raleway);
                        textView.setTypeface(typeface7);
                        break;
                    case "Roboto":
                        Typeface typeface8 = ResourcesCompat.getFont(ChangFontStype.this,R.font.roboto);
                        textView.setTypeface(typeface8);
                        break;
                    case "Roboto Condensed":
                        Typeface typeface9 = ResourcesCompat.getFont(ChangFontStype.this,R.font.roboto_condensed);
                        textView.setTypeface(typeface9);
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
                textView.setTextSize(temp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PDFUtil.createPDF(ChangFontStype.this, textView, "nam_mom.pdf");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        list.add(new Category(getResources().getString(R.string.time_new_roman)));
        list.add(new Category(getResources().getString(R.string.arial)));
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