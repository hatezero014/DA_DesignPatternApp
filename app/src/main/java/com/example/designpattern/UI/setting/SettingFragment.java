package com.example.designpattern.UI.setting;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.designpattern.BookmarkActivity;
import com.example.designpattern.ChangeLanguage;
import com.example.designpattern.ContactActivity;
import com.example.designpattern.FovouriteActivity;
import com.example.designpattern.Models.LanguageApp;
import com.example.designpattern.R;
import com.example.designpattern.Services.LanguageAppService;


public class SettingFragment extends Fragment {

    Dialog dialog;
    TextView textViewSubTheme, textViewSubLanguage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        textViewSubTheme = view.findViewById(R.id.textviewSubTheme);
        textViewSubLanguage = view.findViewById(R.id.textviewSubLanguage);

        initLanguage();
        customDialog();

        // Thiết lập sự kiện khi click vào phần chọn chủ đề
        view.findViewById(R.id.layoutTheme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show(); // Hiển thị dialog thay đổi chủ đề
            }
        });

        // Thiết lập sự kiện khi click vào phần chọn ngôn ngữ
        view.findViewById(R.id.layoutLanguage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLayoutLanguageClick(); // Xử lý khi click vào phần chọn ngôn ngữ
            }
        });

        // Thiết lập sự kiện khi click vào phần chọn gửi phản hồi
        view.findViewById(R.id.layoutFeedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLayoutFeedbackClick(); // Xử lý khi click vào phần chọn gửi phản hồi
            }
        });

        view.findViewById(R.id.layoutBookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookmarkActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.layoutFavourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FovouriteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onLayoutLanguageClick() {
        Intent intent = new Intent(getContext(), ChangeLanguage.class);
        startActivity(intent);
        initLanguage();
    }

    void initLanguage() {
        LanguageAppService languageService = new LanguageAppService(getContext());
        LanguageApp language = languageService.FindById(LanguageApp.class, 1);
        if (language.getIsActive() == 1) {
            textViewSubLanguage.setText(getString(R.string.language_vi));
        }
        else {
            textViewSubLanguage.setText(getString(R.string.language_en));
        }
    }

    public void onLayoutFeedbackClick() {
        Intent intent = new Intent(getContext(), ContactActivity.class);
        startActivity(intent);
    }

    public void customDialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_display_mode);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.custom_dialog_display_mode));
        dialog.setCancelable(true);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        RadioButton rBtnLight = dialog.findViewById(R.id.rBtnLight);
        RadioButton rBtnDark = dialog.findViewById(R.id.rBtnDark);
        RadioButton rBtnSystem = dialog.findViewById(R.id.rBtnSystem);

        int displayMode = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE).getInt("displayMode", 2);
        if (displayMode == 0) {
            rBtnLight.setChecked(true);
            textViewSubTheme.setText(getString(R.string.display_mode_light));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else if (displayMode == 1){
            rBtnDark.setChecked(true);
            textViewSubTheme.setText(getString(R.string.display_mode_dark));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            rBtnSystem.setChecked(true);
            textViewSubTheme.setText(getString(R.string.display_mode_system));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        rBtnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE).edit();
                editor.putInt("displayMode", 0);
                editor.apply();
                textViewSubTheme.setText(getString(R.string.display_mode_light));
                dialog.dismiss();
            }
        });

        rBtnDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE).edit();
                editor.putInt("displayMode", 1);
                editor.apply();
                textViewSubTheme.setText(getString(R.string.display_mode_dark));
                dialog.dismiss();
            }
        });

        rBtnSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                SharedPreferences.Editor editor = getContext().getSharedPreferences("MODE", Context.MODE_PRIVATE).edit();
                editor.putInt("displayMode", 2);
                editor.apply();
                textViewSubTheme.setText(getString(R.string.display_mode_system));
                dialog.dismiss();
            }
        });
    }
}