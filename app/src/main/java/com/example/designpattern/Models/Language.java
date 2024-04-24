package com.example.designpattern.Models;

public class Language {
    int Id;
    String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Language() {
    }

    public Language(String name) {
        Name = name;
    }
}
