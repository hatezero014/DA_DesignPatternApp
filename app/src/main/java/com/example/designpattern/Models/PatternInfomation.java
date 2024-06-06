package com.example.designpattern.Models;

public class PatternInfomation {
    public PatternInfomation() {
    }

    int Id;
    int PatternId;
    String Intent;
    String Problem;
    String Solution;
    String Structure;
    String Pseudocode;
    String Applicability;
    String HowToImplement;
    String ProsAndCons;
    String PseudocodeImage;
    String StructureImage;

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

    public String getStructure() {
        return Structure;
    }

    public void setStructure(String structure) {
        Structure = structure;
    }

    public String getPseudocode() {
        return Pseudocode;
    }

    public void setPseudocode(String pseudocode) {
        Pseudocode = pseudocode;
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

    public String getPseudocodeImage() {
        return PseudocodeImage;
    }

    public void setPseudocodeImage(String pseudocodeImage) {
        PseudocodeImage = pseudocodeImage;
    }

    public String getStructureImage() {
        return StructureImage;
    }

    public void setStructureImage(String structureImage) {
        StructureImage = structureImage;
    }

    public PatternInfomation(int id, int patternId, String intent, String problem, String solution, String structure, String pseudocode, String applicability, String howToImplement, String prosAndCons, String pseudocodeImage, String structureImage) {
        Id = id;
        PatternId = patternId;
        Intent = intent;
        Problem = problem;
        Solution = solution;
        Structure = structure;
        Pseudocode = pseudocode;
        Applicability = applicability;
        HowToImplement = howToImplement;
        ProsAndCons = prosAndCons;
        PseudocodeImage = pseudocodeImage;
        StructureImage = structureImage;
    }
}
