package com.example.demo;

public class Book {
    private int id;
    private String name;
    private String author;
    private String genre;
    private int release_year;
    private int age_limit;

    public Book(int id, String name, String author, String genre, int release_year, int age_limit) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.release_year = release_year;
        this.age_limit = age_limit;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public int getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(int age_limit) {
        this.age_limit = age_limit;
    }
}
