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
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Services.BookmarkService;
import com.example.designpattern.Services.FavouriteService;

import java.util.List;

public class FovouriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ListItemAdapter listItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fovourite);

        recyclerView = findViewById(R.id.recyclerViewFavourite);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        LoadFavourite();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void LoadFavourite() {
        FavouriteService bookmarkService = new FavouriteService(this);
        List<Pattern> patterns = bookmarkService.FindFavouriteByPatternId(Pattern.class);
        if(patterns != null) {
            listItemAdapter = new ListItemAdapter(this, patterns);
            recyclerView.setAdapter(listItemAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }
}