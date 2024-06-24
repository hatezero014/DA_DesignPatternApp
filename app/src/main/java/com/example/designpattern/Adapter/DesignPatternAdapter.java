package com.example.designpattern.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.PatternQuestion;
import com.example.designpattern.Models.QuestionButton;
import com.example.designpattern.R;

import com.example.designpattern.R;
import com.example.designpattern.Services.PatternQuestionService;
import com.example.designpattern.Services.PatternService;

import java.util.ArrayList;
import java.util.List;

public class DesignPatternAdapter extends RecyclerView.Adapter<DesignPatternAdapter.DesignPatternHolder>{
    private Context context;

    PatternService patternService;

    public DesignPatternAdapter(Context context) {
        this.context = context;
    }
    private List<QuestionButton> questionButtons;
    public void setData(List<QuestionButton> list){
        this.questionButtons = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DesignPatternAdapter.DesignPatternHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_pattern_name,parent,false);
        return new DesignPatternHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignPatternHolder holder, int position) {
        QuestionButton questionButton = questionButtons.get(position);
        if(questionButton == null){
            return;
        }

        holder.setFormat(questionButton);
    }

    @Override
    public int getItemCount() {
        if(questionButtons!=null)
            return questionButtons.size();
        return 0;
    }

    public class DesignPatternHolder extends RecyclerView.ViewHolder{
        private CardView cv_question_button;
        private TextView tv_pattern, tv_status;
        private ImageView img_pattern;
        public DesignPatternHolder(@NonNull View itemView) {
            super(itemView);
            tv_pattern = itemView.findViewById(R.id.tv_pattern);
            img_pattern = itemView.findViewById(R.id.img_hinh);
            tv_status = itemView.findViewById(R.id.tv_status);
            cv_question_button = itemView.findViewById(R.id.cv_question_button);
        }

        public void setFormat(QuestionButton questionButton) {
            tv_pattern.setText(questionButton.getPatternName());
            //int status = questionButton.getStatus();
            String image = questionButton.getImgPattern();
            int imgResourceId = context.getResources().getIdentifier(image,"drawable", context.getPackageName());
            Drawable drawable = context.getDrawable(imgResourceId);
            img_pattern.setImageDrawable(drawable);



            setStatus(questionButton);
        }

        public void setStatus(QuestionButton questionButton){

            List<PatternQuestion> list = getListResult(questionButton.getPatternName());
            int countCorrectAnswer = 0;
            for(PatternQuestion patternQuestion : list){
                if(patternQuestion.getIsCorrect() == 1){
                    countCorrectAnswer++;
                }
            }

//            PatternService patternService = new PatternService(context);
//            Pattern pattern = patternService.getPatternRow(questionButton.getPatternName());
//            if(pattern.getIsDone() == 1){
//                tv_status.setText(R.string.completed);
//            }
//            else tv_status.setText(R.string.not_completed);
            if(countCorrectAnswer!=0){
                tv_status.setTextSize(20);
                tv_status.setText(countCorrectAnswer + "/5");
            }
            else if(!getListPatternNameisDone(questionButton.getPatternName())){
                tv_status.setTextSize(16);
                tv_status.setText(R.string.not_completed);
            }
            else if(getListPatternNameisDone(questionButton.getPatternName())){
                tv_status.setTextSize(16);
                tv_status.setText(R.string.completed);
            }
        }

        private boolean getListPatternNameisDone(String patternName){
            patternService = new PatternService(context);
            List<Pattern> list= patternService.GetPatternNameDone(Pattern.class, patternName);
            if(list.size()==0) {
                return false;
            }
            return true;
        }

        private List<PatternQuestion> getListResult(String PatternName) {
//        patternQuestionService = new PatternQuestionService(this);
//        List<PatternQuestion> list = patternQuestionService.GetAll(PatternQuestion.class);
//        return list;
            PatternService patternService;
            PatternQuestionService patternQuestionService;
            List<PatternQuestion> patternQuestionList = new ArrayList<>();
            patternService = new PatternService(context);
            List<Pattern> patternList = patternService.GetPatternIdByName(PatternName);
            int PatternId = 0;
            for(Pattern pattern : patternList){
                PatternId = pattern.getId();
            }
            if(PatternId != 0){
                patternQuestionService = new PatternQuestionService(context);
                patternQuestionList = patternQuestionService.GetQuestionByPatternId(PatternQuestion.class, String.valueOf(PatternId));

            }
            return patternQuestionList;
        }
    }
}