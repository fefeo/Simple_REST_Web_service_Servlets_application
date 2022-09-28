package com.example.ztp_proj2.beans;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Book {
    protected String id;
    protected String title;
    protected String author;
    protected int year;

    public Book (String id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String toString(){
        return title+", "+author+", "+year;
    }

    public static String idCreator() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }
    public String getId() { return id;}

    public static List FillExampleBooks(List list){
        list.add(new Book(idCreator(),"Pamiętnik Pułku Jazdy Wołyńskiej", "Karol Różycki", 2022));
        list.add(new Book(idCreator(),"Przygody Tomka Sawyera", "Mark Twain", 1876));
        list.add(new Book(idCreator(),"Dzieci z Bullerbyn ", "Astrid Lindgren", 1947));
        return list;
    }
}
