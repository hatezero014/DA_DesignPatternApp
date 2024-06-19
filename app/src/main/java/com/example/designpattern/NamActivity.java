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
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Services.PatternService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NamActivity extends BaseActivity {
    Set<String> selectedTypeChips = new HashSet<>();
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
        loadPattern(new ArrayList<>());
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

            ChipGroup chipContainer2 = customFilterView.findViewById(R.id.chipContainer2);

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
                                    List<String> typeList = new ArrayList<>(selectedTypeChips);
                                    loadPattern(typeList);
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
                    List<String> typeList = new ArrayList<>(selectedTypeChips);
                    loadPattern(typeList);
                }
            });

            customFilterDialog.show();
        }
        return true;
    }
    void loadPattern(List<String> types) {
        ArrayList<Pattern> result = new ArrayList<>();
        for (String type : types) {
            ArrayList<Pattern> patterns = patternService.GetAllByCatalog(type);
            result.addAll(patterns);
        }
        if (result.isEmpty()) {
            result = patternService.GetAll(Pattern.class);
        }
        Map<String, Integer> catalogOrder = new HashMap<>();
        catalogOrder.put("Creational Patterns", 1);
        catalogOrder.put("Structural Patterns", 2);
        catalogOrder.put("Behavioral Patterns", 3);

        // Sắp xếp danh sách theo catalog và sau đó theo name
        Collections.sort(result, new Comparator<Pattern>() {
            @Override
            public int compare(Pattern pattern1, Pattern pattern2) {
                // So sánh catalog dựa trên thứ tự ưu tiên
                int catalogCompare = catalogOrder.get(pattern1.getCatalog()).compareTo(catalogOrder.get(pattern2.getCatalog()));
                if (catalogCompare != 0) {
                    return catalogCompare;
                } else {
                    // Nếu catalog giống nhau, so sánh name
                    return pattern1.getName().compareTo(pattern2.getName());
                }
            }
        });
        adapter = new ListItemAdapter(this, result, new IClickItemListener() {
            @Override
            public void onClickItem(String itemType) {
                onClickGoToHomePage(itemType);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void onClickGoToHomePage(String PatternName){
        Intent intent = new Intent(this, ShowDesignPatternInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", PatternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}