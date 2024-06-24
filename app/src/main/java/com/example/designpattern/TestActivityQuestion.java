package com.example.designpattern;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TestActivityQuestion extends BaseActivity {

    TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    Button btnCheck;
    TextView tvSkip;
    Boolean an1 = false, an2 = false, an3 = false, an4 = false;

    String correctans = "Đáp án: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_question);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        tvAnswer1 = findViewById(R.id.answer1);
        tvAnswer2 = findViewById(R.id.answer2);
        tvAnswer3 = findViewById(R.id.answer3);
        tvAnswer4 = findViewById(R.id.answer4);
        btnCheck = findViewById(R.id.btn_Check);
        tvSkip = findViewById(R.id.tv_skip);

        tvAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(an1 == false){
                   SetTrueFalse(true, false, false, false);
                   btnCheck.setBackgroundResource(R.drawable.background_correct_answer);
               }
                SetBackroundTextView(tvAnswer1, an1);
                SetBackroundTextView(tvAnswer2, an2);
                SetBackroundTextView(tvAnswer3, an3);
                SetBackroundTextView(tvAnswer4, an4);
            }
        });

        tvAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(an2 == false){
                    SetTrueFalse(false, true, false, false);
                    btnCheck.setBackgroundResource(R.drawable.background_correct_answer);
                }
                SetBackroundTextView(tvAnswer1, an1);
                SetBackroundTextView(tvAnswer2, an2);
                SetBackroundTextView(tvAnswer3, an3);
                SetBackroundTextView(tvAnswer4, an4);
            }
        });

        tvAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(an3 == false){
                    SetTrueFalse(false, false, true, false);
                    btnCheck.setBackgroundResource(R.drawable.background_correct_answer);

                }
                SetBackroundTextView(tvAnswer1, an1);
                SetBackroundTextView(tvAnswer2, an2);
                SetBackroundTextView(tvAnswer3, an3);
                SetBackroundTextView(tvAnswer4, an4);
            }
        });

        tvAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(an4 == false){
                    SetTrueFalse(false, false, false, true);
                    btnCheck.setBackgroundResource(R.drawable.background_correct_answer);

                }
                SetBackroundTextView(tvAnswer1, an1);
                SetBackroundTextView(tvAnswer2, an2);
                SetBackroundTextView(tvAnswer3, an3);
                SetBackroundTextView(tvAnswer4, an4);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailDiaLog(Gravity.BOTTOM, false);
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm bỏ qua câu hỏi ở đây nha Phong mõm
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void SetBackroundTextView(TextView textview, Boolean ans){
        if(ans) textview.setBackgroundResource(R.drawable.bg_green_corner_30);
        else textview.setBackgroundResource(R.drawable.background_answer);
    }

    private void SetTrueFalse(Boolean ans1, Boolean ans2, Boolean ans3, Boolean ans4){
        an1 = ans1;
        an2 = ans2;
        an3 = ans3;
        an4 = ans4;
    }

    private void openDetailDiaLog(int gravity, Boolean layouttruefalse) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.correct_dialog);

        Window window = dialog.getWindow();
        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttibutes = window.getAttributes();
        windowAttibutes.gravity = gravity;
        window.setAttributes(windowAttibutes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        LinearLayout linearLayout = dialog.findViewById(R.id.linear_dialog);
        TextView tvTitleCorrect = dialog.findViewById(R.id.TitleCorrect);
        TextView tvContentAnswer = dialog.findViewById(R.id.ContentAnswer);
        Button btn_Continue = dialog.findViewById(R.id.btn_Continue);
        ImageView img_dog = dialog.findViewById(R.id.img_dog);

        if(layouttruefalse)
        {
            linearLayout.setBackgroundResource(R.drawable.background_green);
            tvTitleCorrect.setText("Đáp án chính xác");
            img_dog.setImageResource(R.drawable.dog);
        }
        else
        {
            linearLayout.setBackgroundResource(R.drawable.bg_incorrect);
            tvTitleCorrect.setText("Đáp án sai");
            img_dog.setImageResource(R.drawable.dogsad);
        }
        tvContentAnswer.setText(correctans);



        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}