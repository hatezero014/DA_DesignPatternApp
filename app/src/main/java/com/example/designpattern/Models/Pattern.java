package com.example.designpattern.Models;

public class Pattern {
    int Id;
    String Name;
    String Catalog;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    String Language;

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

    public String getCatalog() {
        return Catalog;
    }

    public void setCatalog(String catalog) {
        Catalog = catalog;
    }

    public Pattern() {
    }

    public Pattern(String name, String catalog, String language) {
        Name = name;
        Catalog = catalog;
        Language = language;
    }
}
