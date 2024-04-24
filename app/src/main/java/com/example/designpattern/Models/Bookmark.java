package com.example.designpattern.Models;

public class Bookmark{
    int Id;
    int PatternId;

    public Bookmark() {
    }

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

    public Bookmark(int patternId) {
        PatternId = patternId;
    }
}
