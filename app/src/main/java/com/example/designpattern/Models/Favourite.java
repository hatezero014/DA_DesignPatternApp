package com.example.designpattern.Models;

public class Favourite {
    int Id;
    int PatternId;

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

    public Favourite() {
    }

    public Favourite(int patternId) {
        PatternId = patternId;
    }
}
