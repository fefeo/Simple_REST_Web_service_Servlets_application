package com.example.ztp_proj2.requests;

public class CreateBookRequest {
    private String title;
    private String author;
    private int year;

    public CreateBookRequest(String title,String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
    public String getTitle() {return title;}

    public String getAuthor() {return author;}

    public Integer getYear() {return year;}
}
