package com.example.designpattern.Models;

public class AnswerIsCorrect extends QuestionButton{
    public AnswerIsCorrect(String patternName, String imgPattern, int status, int count) {
        super(patternName, imgPattern, status);
        Count = count;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    int Count;
}
