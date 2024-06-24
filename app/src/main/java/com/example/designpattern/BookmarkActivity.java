package com.example.designpattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.ListItemAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.Bookmark;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Services.BookmarkService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkActivity extends BaseActivity {
    RecyclerView recyclerView;

    ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookmark);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.BookmarkName);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewBookmark);

        LoadBookmark();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void LoadBookmark() {
        BookmarkService bookmarkService = new BookmarkService(this);
        List<Pattern> patterns = bookmarkService.FindBookmarkByPatternId(Pattern.class);

        Map<String, Integer> catalogOrder = new HashMap<>();
        catalogOrder.put("Creational Patterns", 1);
        catalogOrder.put("Structural Patterns", 2);
        catalogOrder.put("Behavioral Patterns", 3);

        List<Bookmark> bookmarks = bookmarkService.GetAll(Bookmark.class);

        // Extract bookmarked PatternIds
        List<Integer> bookmarkedPatternIds = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            bookmarkedPatternIds.add(bookmark.getPatternId());
        }

        Collections.sort(patterns, new Comparator<Pattern>() {
            @Override
            public int compare(Pattern pattern1, Pattern pattern2) {
                boolean isPattern1Bookmarked = bookmarkedPatternIds.contains(pattern1.getId());
                boolean isPattern2Bookmarked = bookmarkedPatternIds.contains(pattern2.getId());

                if (isPattern1Bookmarked && !isPattern2Bookmarked) {
                    return -1; // pattern1 should come before pattern2
                } else if (!isPattern1Bookmarked && isPattern2Bookmarked) {
                    return 1; // pattern2 should come before pattern1
                }

                // Compare catalog order if both patterns are either bookmarked or not bookmarked
                int catalogCompare = catalogOrder.get(pattern1.getCatalog()).compareTo(catalogOrder.get(pattern2.getCatalog()));
                if (catalogCompare != 0) {
                    return catalogCompare;
                } else {
                    // If catalogs are the same, compare by name
                    return pattern1.getName().compareTo(pattern2.getName());
                }
            }
        });

        if(patterns != null) {
            listItemAdapter = new ListItemAdapter(this, patterns, new IClickItemListener() {
                @Override
                public void onClickItem(String itemType) {
                    onClickGoToHomePage(itemType);
                }
            });
            recyclerView.setAdapter(listItemAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }

        if(patterns.size() == 0) {
            findViewById(R.id.tv_nullBookmark).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void onClickGoToHomePage(String PatternName){
        Intent intent = new Intent(this, ShowDesignPatternInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", PatternName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}