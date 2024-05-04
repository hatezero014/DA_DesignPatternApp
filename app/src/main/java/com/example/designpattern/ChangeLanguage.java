package com.example.designpattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.designpattern.Models.LanguageApp;
import com.example.designpattern.Services.LanguageAppService;

import java.util.ArrayList;

public class ChangeLanguage extends BaseActivity {
    ListView listViewLanguage;
    LanguageListViewAdapter languageListViewAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_language);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.change_language));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LanguageAppService languageService = new LanguageAppService(this);
        ArrayList<LanguageApp> listLanguage = languageService.GetAll(LanguageApp.class);

        languageListViewAdapter = new LanguageListViewAdapter(listLanguage);

        listViewLanguage = findViewById(R.id.listView);
        listViewLanguage.setAdapter(languageListViewAdapter);

        listViewLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LanguageApp language = (LanguageApp) languageListViewAdapter.getItem(position);
                int _id = (id == 1) ? 2 : 1;
                LanguageApp _language = (LanguageApp) languageListViewAdapter.getItem(_id - 1);

                LanguageApp lan = languageService.FindById(LanguageApp.class, 1);
                if (lan.getIsActive() == 1) {
                    if (lan.getId() == (int)id) {
                        return;
                    }
                }
                else {
                    if (id == 2) {
                        return;
                    }
                }

                languageService.UpdateById(new LanguageApp(language.getName(), language.getCode(), 1), (int)id);
                languageService.UpdateById(new LanguageApp(_language.getName(), _language.getCode(), 0), _id);

                languageListViewAdapter.setSelectedItemId(language.getId());
                setLocale(language.getCode());
                recreateAllActivities(ChangeLanguage.this);
            }
        });
    }
    void recreateAllActivities(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    class LanguageListViewAdapter extends BaseAdapter {
        private int selectedItemId = -1;
        ArrayList<LanguageApp> listLanguage;

        LanguageListViewAdapter(ArrayList<LanguageApp> listProduct) {
            this.listLanguage = listProduct;
        }
        public void setSelectedItemId(int selectedItemId) {
            if (this.selectedItemId == selectedItemId)
                return;
            this.selectedItemId = selectedItemId;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return listLanguage.size();
        }
        @Override
        public Object getItem(int position) {
            return listLanguage.get(position);
        }
        @Override
        public long getItemId(int position) {
            return listLanguage.get(position).getId();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewLanguage;
            if (convertView == null) {
                viewLanguage = View.inflate(parent.getContext(), R.layout.language_view, null);
            } else viewLanguage = convertView;
            LanguageApp language = (LanguageApp) getItem(position);
            TextView nameTextView = viewLanguage.findViewById(R.id.nameLanguage);
            TextView subTextView = viewLanguage.findViewById(R.id.subLanguage);
            ImageView imageView = viewLanguage.findViewById(R.id.imageView);

            nameTextView.setText(language.getName());
            if (language.getCode().equals("vi")) {
                subTextView.setText(getString(R.string.language_vi));
            } else {
                subTextView.setText(getString(R.string.language_en));
            }

            if (language.getIsActive() == 1) {
                selectedItemId = language.getId();
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }
            return viewLanguage;
        }
    }
}