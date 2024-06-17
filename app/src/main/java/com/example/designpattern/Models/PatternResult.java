package com.example.designpattern.Models;

public class PatternResult {
    int Id;
    int PatternId;
    String CSharpResult;
    String CppResult;
    String GoResult;
    String JavaResult;
    String PHPResult;
    String PythonResult;
    String RubyResult;
    String RustResult;
    String SwiftResult;

    public PatternResult() {
    }

    String TypeScriptResult;

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

    public String getCSharpResult() {
        return CSharpResult;
    }

    public void setCSharpResult(String CSharpResult) {
        this.CSharpResult = CSharpResult;
    }

    public String getCppResult() {
        return CppResult;
    }

    public void setCppResult(String cppResult) {
        CppResult = cppResult;
    }

    public String getGoResult() {
        return GoResult;
    }

    public void setGoResult(String goResult) {
        GoResult = goResult;
    }

    public String getJavaResult() {
        return JavaResult;
    }

    public void setJavaResult(String javaResult) {
        JavaResult = javaResult;
    }

    public String getPHPResult() {
        return PHPResult;
    }

    public void setPHPResult(String PHPResult) {
        this.PHPResult = PHPResult;
    }

    public String getPythonResult() {
        return PythonResult;
    }

    public void setPythonResult(String pythonResult) {
        PythonResult = pythonResult;
    }

    public String getRubyResult() {
        return RubyResult;
    }

    public void setRubyResult(String rubyResult) {
        RubyResult = rubyResult;
    }

    public String getRustResult() {
        return RustResult;
    }

    public void setRustResult(String rustResult) {
        RustResult = rustResult;
    }

    public String getSwiftResult() {
        return SwiftResult;
    }

    public void setSwiftResult(String swiftResult) {
        SwiftResult = swiftResult;
    }

    public String getTypeScriptResult() {
        return TypeScriptResult;
    }

    public void setTypeScriptResult(String typeScriptResult) {
        TypeScriptResult = typeScriptResult;
    }

    public PatternResult(int id, int patternId, String CSharpResult, String cppResult, String goResult, String javaResult, String PHPResult, String pythonResult, String rubyResult, String rustResult, String swiftResult, String typeScriptResult) {
        Id = id;
        PatternId = patternId;
        this.CSharpResult = CSharpResult;
        CppResult = cppResult;
        GoResult = goResult;
        JavaResult = javaResult;
        this.PHPResult = PHPResult;
        PythonResult = pythonResult;
        RubyResult = rubyResult;
        RustResult = rustResult;
        SwiftResult = swiftResult;
        TypeScriptResult = typeScriptResult;
    }
}
