package com.example.designpattern.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Models.CodeLanguage;
import com.example.designpattern.Models.PatternResult;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternResultService;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private CodeLanguage codeLanguage;
    private final Context context;
    private final String patternName;

    public ResultAdapter(Context context, String patternName) {
        this.context = context;
        this.patternName = patternName;
    }

    public void setData(CodeLanguage codeLanguage){
        this.codeLanguage = codeLanguage;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_code_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        if(codeLanguage == null){
            return;
        }

        holder.setResult(codeLanguage.getLanguage());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_result;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_result = itemView.findViewById(R.id.tv_code_result);
        }

        public void setResult(String language) {
            PatternResultService patternResultService = new PatternResultService(context);
            PatternResult patternResult = patternResultService.getCodeResult(patternName);

            String result = getResult(patternResult, language);

            tv_result.setText(result);
        }

        private String getResult(PatternResult patternResult, String lang){
            String result = null;


            switch (lang){
                case "C#":
                    result = patternResult.getCSharpResult();
                    break;
                case "C++":
                    result = patternResult.getCppResult();
                    break;
                case "Go":
                    result = patternResult.getGoResult();
                    break;
                case "Java":
                    result = patternResult.getJavaResult();
                    break;
                case "PHP":
                    result = patternResult.getPHPResult();
                    break;
                case "Python":
                    result = patternResult.getPythonResult();
                    break;
                case "Ruby":
                    result = patternResult.getRubyResult();
                    break;
                case "Rust":
                    result = patternResult.getRustResult();
                    break;
                case "Swift":
                    result = patternResult.getSwiftResult();
                    break;
                default:
                    result = patternResult.getTypeScriptResult();
                    break;
            }
            return result;
        }
    }
}
