package com.example.ztp_proj2.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.ztp_proj2.beans.User;
import com.example.ztp_proj2.requests.CreateBookRequest;
import com.example.ztp_proj2.responses.Response;
import com.google.gson.Gson;
import com.example.ztp_proj2.responses.ExceptionResponse;
import com.example.ztp_proj2.beans.Book;
import com.example.ztp_proj2.responses.GetDashboardResponse;

@WebServlet(name = "dashboard", value = "/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("In DashboardServlet GET");
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        try {
            List<Book> books = (List<Book>) getServletContext().getAttribute("books");
            GetDashboardResponse res = new GetDashboardResponse(books, 200);
            gson.toJson(res, response.getWriter());
        } catch (Exception ex) {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);
            gson.toJson(exResponse, response.getWriter());
        }
        System.out.println("Out DashboardServlet GET");
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        User currenUser = (User)request.getSession().getAttribute("currentUser");
        try {
            if("admin".equals(currenUser.login)) {
                CreateBookRequest createBookRequest = gson.fromJson(request.getReader(), CreateBookRequest.class);
                List<Book> books = (List<Book>) getServletContext().getAttribute("books");
                Book newBook = createBook(createBookRequest.getTitle(), createBookRequest.getAuthor(), createBookRequest.getYear());
                books.add(newBook);
                GetDashboardResponse res = new GetDashboardResponse(books, 200);
                gson.toJson(res, response.getWriter());
            } else {
                Response failResponse = new Response("Only ADMIN role can add new book!", 400);
                response.setStatus(400);
                gson.toJson(failResponse, response.getWriter());
            }
        } catch (Exception ex) {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);
            gson.toJson(exResponse, response.getWriter());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        try {
            deleteBook(request, response);
        } catch (Exception ex) {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);
            gson.toJson(exResponse, response.getWriter());
        }
    }
    private Book createBook(String title, String author, int year) {
        return new Book(Book.idCreator(), title, author, year);
    }

    private ArrayList<Object> combineResponse(String message, Response response, Book book, List list) {
        ArrayList<Object> res = new ArrayList<>();
        res.add(message);
        res.add(book);
        res.add(list);
        res.add(response);
        return res;
    }
    private void deleteBook (HttpServletRequest request, HttpServletResponse response) throws IOException {
        User currenUser = (User) request.getSession().getAttribute("currentUser");
        Gson gson = new Gson();

        if ("admin".equals(currenUser.login)) {
            String bookToDelete = request.getParameter("id");
            List<Book> books = (List<Book>) getServletContext().getAttribute("books");
            String message = "Book has been successfully deleted! Deleted book:";
            Response correctResponse = new Response("OK", 200);
            Response wrongIdResponse = new Response("Wrong book id!", 400);
            boolean checResponse = true;

            for (Book book : books) {
                if (book.getId().equals(bookToDelete)) {
                    Book deletedBook = book;
                    books.remove(book);
                    gson.toJson(combineResponse(message, correctResponse, deletedBook, books), response.getWriter());
                    checResponse = true;
                    break;
                } else {
                    checResponse = false;
                }
            }
            if(checResponse == false) {
                response.setStatus(400);
                gson.toJson(wrongIdResponse, response.getWriter());
            }
        } else {
            Response failResponse = new Response("Only ADMIN role can delete a book!", 400);
            response.setStatus(400);
            gson.toJson(failResponse, response.getWriter());
        }
    }

}
