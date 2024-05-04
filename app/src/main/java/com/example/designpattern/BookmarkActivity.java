package com.example.designpattern;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Adapter.ListItemAdapter;
import com.example.designpattern.Models.Bookmark;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Services.BookmarkService;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookmark);

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
            listItemAdapter = new ListItemAdapter(this, patterns);
            recyclerView.setAdapter(listItemAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }
}