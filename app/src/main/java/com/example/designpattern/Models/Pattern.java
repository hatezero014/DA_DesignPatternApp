package com.example.designpattern.Models;

public class Pattern {
    int Id;
    String Name;
    String Catalog;
    String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

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

    public Pattern(String name, String catalog, String language, String image) {
        Name = name;
        Catalog = catalog;
        Language = language;
        Image = image;
    }

    public Pattern(String name) {
        Name = name;
    }
}
