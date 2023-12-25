package com.sasha.myapp;

public class Game {
    private long id;
    private String title;
    private String genre;
    private String platform;

    public Game(long id, String title, String genre, String platform) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
    }

    public Game() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}