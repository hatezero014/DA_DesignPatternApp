package com.example.designpattern.Models;

public class PatternQuestion {
    int Id;
    int PatternId;
    String Question;
    String Answer1;
    String Answer2;
    String Answer3;
    String Answer4;
    int AnsCorrect;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPatternId() {
        return PatternId;
    }

    public void setPatternId(int patternId) {
        PatternId = patternId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }

    public int getAnsCorrect() {
        return AnsCorrect;
    }

    public void setAnsCorrect(int ansCorrect) {
        AnsCorrect = ansCorrect;
    }

    public PatternQuestion(int id, int patternId, String question, String answer1, String answer2, String answer3, String answer4, int ansCorrect) {
        Id = id;
        PatternId = patternId;
        Question = question;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
        AnsCorrect = ansCorrect;
    }

    public PatternQuestion() {
    }
}
