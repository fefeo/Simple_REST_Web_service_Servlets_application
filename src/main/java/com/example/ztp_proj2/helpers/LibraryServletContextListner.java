package com.example.ztp_proj2.helpers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.ztp_proj2.beans.Book;

@WebListener
public class LibraryServletContextListner implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public LibraryServletContextListner() {}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<Book> books = new ArrayList<>();
        Book.FillExampleBooks(books);
        sce.getServletContext().setAttribute("books", books);
    }
}

