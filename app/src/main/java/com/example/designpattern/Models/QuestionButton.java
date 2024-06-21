package com.example.designpattern.Models;

public class QuestionButton {
    String patternName;
    String imgPattern;
    int status;

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getImgPattern() {
        return imgPattern;
    }

    public void setImgPattern(String imgPattern) {
        this.imgPattern = imgPattern;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public QuestionButton(String patternName, String imgPattern, int status) {
        this.patternName = patternName;
        this.imgPattern = imgPattern;
        this.status = status;
    }
}
