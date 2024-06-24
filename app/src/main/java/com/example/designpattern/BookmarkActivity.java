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
import java.util.List;

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