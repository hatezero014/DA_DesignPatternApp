package com.example.designpattern.Models;

public class PatternInfoVi {
    int Id;
    int PatternId;
    String IntentVi;
    String ProblemVi;
    String SolutionVi;
    String StructureVi;
    String ApplicabilityVi;
    String HowToImplementVi;

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

    public String getIntentVi() {
        return IntentVi;
    }

    public void setIntentVi(String intentVi) {
        IntentVi = intentVi;
    }

    public String getProblemVi() {
        return ProblemVi;
    }

    public void setProblemVi(String problemVi) {
        ProblemVi = problemVi;
    }

    public String getSolutionVi() {
        return SolutionVi;
    }

    public void setSolutionVi(String solutionVi) {
        SolutionVi = solutionVi;
    }

    public String getStructureVi() {
        return StructureVi;
    }

    public void setStructureVi(String structureVi) {
        StructureVi = structureVi;
    }

    public String getApplicabilityVi() {
        return ApplicabilityVi;
    }

    public void setApplicabilityVi(String applicabilityVi) {
        ApplicabilityVi = applicabilityVi;
    }

    public String getHowToImplementVi() {
        return HowToImplementVi;
    }

    public void setHowToImplementVi(String howToImplementVi) {
        HowToImplementVi = howToImplementVi;
    }

    public String getProsAndConsVi() {
        return ProsAndConsVi;
    }

    public void setProsAndConsVi(String prosAndConsVi) {
        ProsAndConsVi = prosAndConsVi;
    }

    public PatternInfoVi() {
    }

    public PatternInfoVi(int id, int patternId, String intentVi, String problemVi, String solutionVi, String structureVi, String applicabilityVi, String howToImplementVi, String prosAndConsVi) {
        Id = id;
        PatternId = patternId;
        IntentVi = intentVi;
        ProblemVi = problemVi;
        SolutionVi = solutionVi;
        StructureVi = structureVi;
        ApplicabilityVi = applicabilityVi;
        HowToImplementVi = howToImplementVi;
        ProsAndConsVi = prosAndConsVi;
    }

    String ProsAndConsVi;
}
