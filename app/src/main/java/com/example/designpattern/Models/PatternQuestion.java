package com.example.designpattern.Models;

public class PatternQuestion {
    int Id;
    int PatternId;
    String Question;
    String Answer1;
    String Answer2;
    String Answer3;
    String Answer4;
    String QuestionVi;
    String Answer1Vi;
    String Answer2Vi;
    String Answer3Vi;
    String Answer4Vi;
    int AnsCorrect;

    public PatternQuestion(int patternId, String question, String answer1, String answer2, String answer3, String answer4, String questionVi, String answer1Vi, String answer2Vi, String answer3Vi, String answer4Vi, int ansCorrect, int isCorrect) {

        PatternId = patternId;
        Question = question;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
        QuestionVi = questionVi;
        Answer1Vi = answer1Vi;
        Answer2Vi = answer2Vi;
        Answer3Vi = answer3Vi;
        Answer4Vi = answer4Vi;
        AnsCorrect = ansCorrect;
        this.isCorrect = isCorrect;
    }

    public String getQuestionVi() {
        return QuestionVi;
    }

    public void setQuestionVi(String questionVi) {
        QuestionVi = questionVi;
    }

    public String getAnswer1Vi() {
        return Answer1Vi;
    }

    public void setAnswer1Vi(String answer1Vi) {
        Answer1Vi = answer1Vi;
    }

    public String getAnswer2Vi() {
        return Answer2Vi;
    }

    public void setAnswer2Vi(String answer2Vi) {
        Answer2Vi = answer2Vi;
    }

    public String getAnswer3Vi() {
        return Answer3Vi;
    }

    public void setAnswer3Vi(String answer3Vi) {
        Answer3Vi = answer3Vi;
    }

    public String getAnswer4Vi() {
        return Answer4Vi;
    }

    public void setAnswer4Vi(String answer4Vi) {
        Answer4Vi = answer4Vi;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    int isCorrect;

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

    public PatternQuestion( int patternId, String question, String answer1, String answer2, String answer3, String answer4, int ansCorrect, int isCorrect) {
//        Id = id;
        PatternId = patternId;
        Question = question;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
        AnsCorrect = ansCorrect;
        this.isCorrect = isCorrect;
    }

    public PatternQuestion() {
    }
}
