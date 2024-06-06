package com.example.designpattern.Models;

public class PatternCode {
    int Id;
    int PatternId;
    String CSharp;
    String Cpp;
    String Go;
    String Java;
    String PHP;
    String Python;
    String Ruby;
    String Rust;
    String Swift;
    String TypeScript;

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

    public String getCSharp() {
        return CSharp;
    }

    public void setCSharp(String CSharp) {
        this.CSharp = CSharp;
    }

    public String getCpp() {
        return Cpp;
    }

    public void setCpp(String cpp) {
        Cpp = cpp;
    }

    public String getGo() {
        return Go;
    }

    public void setGo(String go) {
        Go = go;
    }

    public String getJava() {
        return Java;
    }

    public void setJava(String java) {
        Java = java;
    }

    public String getPHP() {
        return PHP;
    }

    public void setPHP(String PHP) {
        this.PHP = PHP;
    }

    public String getPython() {
        return Python;
    }

    public void setPython(String python) {
        Python = python;
    }

    public String getRuby() {
        return Ruby;
    }

    public void setRuby(String ruby) {
        Ruby = ruby;
    }

    public String getRust() {
        return Rust;
    }

    public void setRust(String rust) {
        Rust = rust;
    }

    public String getSwift() {
        return Swift;
    }

    public void setSwift(String swift) {
        Swift = swift;
    }

    public String getTypeScript() {
        return TypeScript;
    }

    public void setTypeScript(String typeScript) {
        TypeScript = typeScript;
    }

    public PatternCode(int id, int patternId, String CSharp, String cpp, String go, String java, String PHP, String python, String ruby, String rust, String swift, String typeScript) {
        Id = id;
        PatternId = patternId;
        this.CSharp = CSharp;
        Cpp = cpp;
        Go = go;
        Java = java;
        this.PHP = PHP;
        Python = python;
        Ruby = ruby;
        Rust = rust;
        Swift = swift;
        TypeScript = typeScript;
    }

    public PatternCode() {
    }
}
