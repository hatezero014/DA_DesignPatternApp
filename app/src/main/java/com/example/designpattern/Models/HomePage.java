package com.example.designpattern.Models;

public class HomePage {
    private String name;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HomePage(String name, int type) {
        this.name = name;
        this.type = type;
    }
}
