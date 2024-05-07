package com.example.designpattern.Models;

public class Content {
    private String title;
    private String content;
    private int img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Content(int img, String title, String content) {
        this.title = title;
        this.content = content;
        this.img = img;
    }
}
