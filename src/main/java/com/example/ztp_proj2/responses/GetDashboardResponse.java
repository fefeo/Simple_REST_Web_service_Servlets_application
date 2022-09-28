package com.example.ztp_proj2.responses;
import com.example.ztp_proj2.beans.Book;

import java.util.List;

public class GetDashboardResponse {
    private List<Book> books;
    private int status;
    public GetDashboardResponse() {
    }
    public GetDashboardResponse(List<Book> books, int status) {
        this.books = books;
        this.status = status;
    }
}
