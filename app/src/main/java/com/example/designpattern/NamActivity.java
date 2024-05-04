package com.example.designpattern;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.ListItemAdapter;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Services.PatternService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NamActivity extends BaseActivity {

    Set<String> selectedLanguageChips = new HashSet<>();
    Set<String> selectedTypeChips = new HashSet<>();
    List<Integer> checkedLanguageIds = new ArrayList<>();
    List<Integer> checkedTypeIds = new ArrayList<>();
    ChipGroup group1, group2;
    TextView textLan, textType;
    PatternService patternService;
    RecyclerView recyclerView;
    ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.app_name));
        }

        group1 = findViewById(R.id.chipContainerLanguage);
        group2 = findViewById(R.id.chipContainerType);
        textLan = findViewById(R.id.textLanguage);
        textType = findViewById(R.id.textType);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        patternService = new PatternService(this);
        loadPattern(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            View customFilterView = getLayoutInflater().inflate(R.layout.custom_filter, null);

            builder.setView(customFilterView);

            AlertDialog customFilterDialog = builder.create();

            customFilterDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            customFilterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            ChipGroup chipContainer1 = customFilterView.findViewById(R.id.chipContainer1);
            ChipGroup chipContainer2 = customFilterView.findViewById(R.id.chipContainer2);

            chipContainer1.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> list) {
                    checkedLanguageIds.clear();
                    selectedLanguageChips.clear();
                    for (Integer index : list) {
                        Chip chip = chipGroup.findViewById(index);
                        for (int i = 0; i < chipContainer1.getChildCount(); i++) {
                            Chip chipInContainer = (Chip) chipContainer1.getChildAt(i);
                            if (chipInContainer.getText() == chip.getText()) {
                                checkedLanguageIds.add(chipInContainer.getId());
                                break;
                            }
                        }
                        selectedLanguageChips.add(chip.getText().toString());
                    }
                }
            });

            chipContainer2.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> list) {
                    checkedTypeIds.clear();
                    selectedTypeChips.clear();
                    for (Integer index : list) {
                        Chip chip = chipGroup.findViewById(index);
                        for (int i = 0; i < chipContainer2.getChildCount(); i++) {
                            Chip chipInContainer = (Chip) chipContainer2.getChildAt(i);
                            if (chipInContainer.getText() == chip.getText()) {
                                checkedTypeIds.add(chipInContainer.getId());
                                break;
                            }
                        }
                        selectedTypeChips.add(chip.getText().toString());
                    }
                }
            });

            customFilterDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    List<Integer> checkedLanguageIdsCopy = new ArrayList<>(checkedLanguageIds);
                    for (int chipId : checkedLanguageIdsCopy) {
                        Chip chip = chipContainer1.findViewById(chipId);
                        if (chip != null) {
                            chip.setChecked(true);
                        }
                    }

                    List<Integer> checkedTypeIdsCopy = new ArrayList<>(checkedTypeIds);
                    for (int chipId : checkedTypeIdsCopy) {
                        Chip chip = chipContainer2.findViewById(chipId);
                        if (chip != null) {
                            chip.setChecked(true);
                        }
                    }
                }
            });

            customFilterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    group1.removeAllViews();

                    LayoutInflater inflater = LayoutInflater.from(NamActivity.this);
                    if (!selectedLanguageChips.isEmpty()) {
                        textLan.setVisibility(View.VISIBLE);
                    }
                    else {
                        textLan.setVisibility(View.GONE);
                    }
                    for (String text : selectedLanguageChips) {
                        Chip chip = (Chip) inflater.inflate(R.layout.item_chip, group1, false);
                        chip.setText(text);
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String chipText = chip.getText().toString();
                                if (selectedLanguageChips.contains(chipText)) {
                                    selectedLanguageChips.remove(chipText);
                                    if (!selectedLanguageChips.isEmpty()) {
                                        textLan.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        textLan.setVisibility(View.GONE);
                                    }
                                    List<String> languageList = new ArrayList<>(selectedLanguageChips);
                                    List<String> typeList = new ArrayList<>(selectedTypeChips);
                                    loadPattern(languageList, typeList);
                                    group1.removeView(chip);
                                    for (int i = 0; i < chipContainer1.getChildCount(); i++) {
                                        Chip chipInContainer = (Chip) chipContainer1.getChildAt(i);
                                        if (chipInContainer.getText() == chip.getText()) {
                                            if (checkedLanguageIds.contains(chipInContainer.getId())) {
                                                checkedLanguageIds.remove(Integer.valueOf(chipInContainer.getId()));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        });
                        group1.addView(chip);
                    }

                    if (!selectedTypeChips.isEmpty()) {
                        textType.setVisibility(View.VISIBLE);
                    }
                    else {
                        textType.setVisibility(View.GONE);
                    }
                    group2.removeAllViews();
                    for (String text : selectedTypeChips) {
                        Chip chip = (Chip) inflater.inflate(R.layout.item_chip, group2, false);
                        chip.setText(text);
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String chipText = chip.getText().toString();
                                if (selectedTypeChips.contains(chipText)) {
                                    selectedTypeChips.remove(chipText);
                                    if (!selectedTypeChips.isEmpty()) {
                                        textType.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        textType.setVisibility(View.GONE);
                                    }
                                    List<String> languageList = new ArrayList<>(selectedLanguageChips);
                                    List<String> typeList = new ArrayList<>(selectedTypeChips);
                                    loadPattern(languageList, typeList);
                                    group2.removeView(chip);
                                    for (int i = 0; i < chipContainer2.getChildCount(); i++) {
                                        Chip chipInContainer = (Chip) chipContainer2.getChildAt(i);
                                        if (chipInContainer.getText() == chip.getText()) {
                                            if (checkedTypeIds.contains(chipInContainer.getId())) {
                                                checkedTypeIds.remove(Integer.valueOf(chipInContainer.getId()));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        });
                        group2.addView(chip);
                    }
                    List<String> languageList = new ArrayList<>(selectedLanguageChips);
                    List<String> typeList = new ArrayList<>(selectedTypeChips);
                    loadPattern(languageList, typeList);
                }
            });

            customFilterDialog.show();
        }
        return true;
    }
    void loadPattern(List<String> languages, List<String> types) {
        ArrayList<Pattern> result = new ArrayList<>();
        for (String language : languages) {
            for (String type : types) {
                ArrayList<Pattern> patterns = patternService.GetAllByLanguageAndCatalog(language, type);
                result.addAll(patterns);
            }
        }
        if (types.isEmpty()) {
            for (String language : languages) {
                ArrayList<Pattern> patterns = patternService.GetAllByLanguage(language);
                result.addAll(patterns);
            }
        }
        if (languages.isEmpty()) {
            for (String type : types) {
                ArrayList<Pattern> patterns = patternService.GetAllByCatalog(type);
                result.addAll(patterns);
            }
        }
        if (result.isEmpty()) {
            result = patternService.GetAll(Pattern.class);
        }
        Collections.sort(result, new Comparator<Pattern>() {
            @Override
            public int compare(Pattern pattern1, Pattern pattern2) {
                return pattern1.getName().compareTo(pattern2.getName());
            }
        });
        adapter = new ListItemAdapter(this, result);
        recyclerView.setAdapter(adapter);
    }
}