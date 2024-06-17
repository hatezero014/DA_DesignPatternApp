package com.example.designpattern;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuestionsActivity extends BaseActivity {
    private TextView tvPatternName;
    private TextView tvQuestion;
    private TextView tvContentQuestion;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.assignment));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();

        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null){
            return;
        }

        String PatternName = (String) bundle.get("PatternName");
        tvPatternName.setText(PatternName);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initUI(){
        tvPatternName = findViewById(R.id.tv_pattern_name);
        tvQuestion = findViewById(R.id.tv_question);
        tvContentQuestion = findViewById(R.id.tv_content_question);
        tvAnswer1 = findViewById(R.id.tv_answer_1);
        tvAnswer2 = findViewById(R.id.tv_answer_2);
        tvAnswer3 = findViewById(R.id.tv_answer_3);
        tvAnswer4 = findViewById(R.id.tv_answer_4);
    }
}