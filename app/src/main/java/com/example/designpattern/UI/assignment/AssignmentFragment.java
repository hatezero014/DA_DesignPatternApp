package com.example.designpattern.UI.assignment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.designpattern.Adapter.QuestionButtonAdapter;
import com.example.designpattern.Interface.IClickItemListener;
import com.example.designpattern.Models.Pattern;
import com.example.designpattern.Models.QuestionButton;
import com.example.designpattern.QuestionsActivity;
import com.example.designpattern.R;
import com.example.designpattern.Services.PatternService;
import com.example.designpattern.ShowInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment {

    private RecyclerView rcv_question_button;
    private QuestionButtonAdapter questionButtonAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        rcv_question_button = view.findViewById(R.id.rcv_question_button);

        questionButtonAdapter = new QuestionButtonAdapter(getContext(), new IClickItemListener() {
            @Override
            public void onClickItem(String itemType) {
                onClickGoToQuestionsActivity(itemType);
            }
        });

        questionButtonAdapter.setData(getData());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_question_button.setLayoutManager(linearLayoutManager);
        rcv_question_button.setAdapter(questionButtonAdapter);

        return view;
    }

    private void onClickGoToQuestionsActivity(String itemType) {
        Intent intent = new Intent(getContext(), QuestionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PatternName", itemType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<QuestionButton> getData() {
        List<QuestionButton> list = new ArrayList<>();

        PatternService patternService = new PatternService(getContext());
        List<Pattern> patternList = patternService.GetAll(Pattern.class);
        for(Pattern pattern: patternList){
            list.add(new QuestionButton(pattern.getName(), pattern.getImage(), pattern.getIsDone()));
        }

        return list;
    }
}