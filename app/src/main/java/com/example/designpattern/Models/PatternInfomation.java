package com.example.designpattern.Models;

public class PatternInfomation {
    public PatternInfomation() {
    }

    int Id;
    int PatternId;
    String Intent;
    String Problem;
    String Solution;
    String RealWorldAnalogy;
    String Structure;
    String Applicability;
    String HowToImplement;
    String ProsAndCons;
    String RelationsWithOtherPatterns;

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

    public String getIntent() {
        return Intent;
    }

    public void setIntent(String intent) {
        Intent = intent;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public String getRealWorldAnalogy() {
        return RealWorldAnalogy;
    }

    public void setRealWorldAnalogy(String realWorldAnalogy) {
        RealWorldAnalogy = realWorldAnalogy;
    }

    public String getStructure() {
        return Structure;
    }

    public void setStructure(String structure) {
        Structure = structure;
    }

    public String getApplicability() {
        return Applicability;
    }

    public void setApplicability(String applicability) {
        Applicability = applicability;
    }

    public String getHowToImplement() {
        return HowToImplement;
    }

    public void setHowToImplement(String howToImplement) {
        HowToImplement = howToImplement;
    }

    public String getProsAndCons() {
        return ProsAndCons;
    }

    public void setProsAndCons(String prosAndCons) {
        ProsAndCons = prosAndCons;
    }

    public String getRelationsWithOtherPatterns() {
        return RelationsWithOtherPatterns;
    }

    public void setRelationsWithOtherPatterns(String relationsWithOtherPatterns) {
        RelationsWithOtherPatterns = relationsWithOtherPatterns;
    }
}
