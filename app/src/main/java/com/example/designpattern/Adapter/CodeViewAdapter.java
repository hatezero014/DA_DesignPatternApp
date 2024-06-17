package com.example.designpattern.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.CodeLanguage;
import com.example.designpattern.Models.PatternCode;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternCodeService;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;
import io.github.kbiakov.codeview.highlight.ColorThemeData;
import io.github.kbiakov.codeview.highlight.Font;
import io.github.kbiakov.codeview.highlight.SyntaxColors;

public class CodeViewAdapter extends RecyclerView.Adapter<CodeViewAdapter.CodeViewViewHolder> {

    private CodeLanguage code;
    private final Context context;
    private final String patternName;

    public CodeViewAdapter(Context context, String patternName) {
        this.context = context;
        this.patternName = patternName;
    }

    public void setData(CodeLanguage code){
        this.code = code;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CodeViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_codeview,parent,false);
        return new CodeViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewViewHolder holder, int position) {
        if(code == null)
            return;
        
        holder.setCode(code.getLanguage());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class CodeViewViewHolder extends RecyclerView.ViewHolder{
        private CodeView codeView;
        public CodeViewViewHolder(@NonNull View itemView) {
            super(itemView);
            codeView = itemView.findViewById(R.id.cv_code);
        }

        public void setCode(String language) {
            PatternCodeService patternCodeService = new PatternCodeService(context);
            PatternCode patternCode = patternCodeService.getCode(patternName);

            String code = getCode(patternCode, language);

            codeView.setCode(code);

//            syntaxColors: Đây là một đối tượng SyntaxColors chứa các màu sắc được sử dụng cho cú pháp của mã, như màu cho từ khóa, chuỗi, bình luận, v.v.
//            0xFFFFFF: Màu nền chính cho CodeView. Trong trường hợp này, nó là màu trắng.
//            0xFF0000: Màu sử dụng cho số dòng. Màu này là màu đỏ.
//            0xEEE8D5: Màu nền cho số dòng. Màu này là một màu be nhạt.
//            0x657B83: Màu sử dụng cho các ghi chú hoặc thông tin phụ. Màu này là một màu xám xanh.

            SyntaxColors syntaxColors = new SyntaxColors(0xA7E22E,
                    0xFA2772,
                    0x66D9EE,
                    0x76715E,
                    0xE6DB74,
                    0xC1C1C1,
                    0xF8F8F0,
                    0xF92672,
                    0xFA2772,
                    0xA6E22E,
                    0xE6DB74);
            ColorThemeData customTheme = new ColorThemeData(syntaxColors,
                    ContextCompat.getColor(context, R.color.numColor),
                    ContextCompat.getColor(context, R.color.bgContent),
                    ContextCompat.getColor(context, R.color.bgNum),
                    ContextCompat.getColor(context, R.color.noteColor));

            codeView.setOptions(Options.Default.get(context)
                    .withLanguage(language)
                    .withCode(code)
                    .withTheme(customTheme)
                    .withFont(Font.Consolas));
        }

        private String getCode(PatternCode patternCode, String lang){
            String code = null;


            switch (lang){
                case "C#":
                    code = patternCode.getCSharp();
                    break;
                case "C++":
                    code = patternCode.getCpp();
                    break;
                case "Go":
                    code = patternCode.getGo();
                    break;
                case "Java":
                    code = patternCode.getJava();
                    break;
                case "PHP":
                    code = patternCode.getPHP();
                    break;
                case "Python":
                    code = patternCode.getPython();
                    break;
                case "Ruby":
                    code = patternCode.getRuby();
                    break;
                case "Rust":
                    code = patternCode.getRust();
                    break;
                case "Swift":
                    code = patternCode.getSwift();
                    break;
                default:
                    code = patternCode.getTypeScript();
                    break;
            }
            return code;
        }
    }
}
