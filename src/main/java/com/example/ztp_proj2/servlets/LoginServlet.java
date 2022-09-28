package com.example.ztp_proj2.servlets;

import com.example.ztp_proj2.beans.Role;
import com.example.ztp_proj2.beans.User;
import com.example.ztp_proj2.requests.LoginRequest;
import com.example.ztp_proj2.responses.Response;
import com.example.ztp_proj2.responses.ExceptionResponse;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("IN LOGINSERVLET");
        Gson gson = new Gson();
        response.setContentType("application/json;charset=UTF-8");

        try {
            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);
            System.out.println(loginRequest);

            if("admin".equals(loginRequest.getUsername())) {
                adminLoginHandler(loginRequest, response, request.getSession());
            } else {
                userLoginHandler(loginRequest, response, request.getSession());
            }
        } catch (Exception ex) {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);
            gson.toJson(exResponse, response.getWriter());
        }


    }
    private void adminLoginHandler(LoginRequest loginRequest, HttpServletResponse response, HttpSession session) throws IOException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Gson gson = new Gson();

        if("admin123".equals(password)) {
            String adminID = getBase64FromString(username);
            Response correctResponse = new Response("Login time: " + new Date(), 200);
            User admin = createAdmin(adminID,username, password);
            session.setAttribute("currentUser", admin);
            response.addCookie(new Cookie("userId", adminID));
            gson.toJson(combineResponse(admin, correctResponse), response.getWriter());
        } else {
            Response failResponse = new Response("Wrong login credentials!", 400);
            response.setStatus(400);
            gson.toJson(failResponse, response.getWriter());
        }
    }

    private void userLoginHandler(LoginRequest loginRequest, HttpServletResponse response, HttpSession session) throws IOException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Gson gson = new Gson();

        if(checkUsersLoginData(username, password)) {
            String userID = getBase64FromString(username);
            Response correctResponse = new Response("Login time: " + new Date(), 200);
            User user = createUser(userID, username, password);
            session.setAttribute("currentUser", user);
            response.addCookie(new Cookie("userId", userID));
            gson.toJson(combineResponse(user, correctResponse), response.getWriter());

        } else {
            System.out.println("daj znać");
            Response failResponse = new Response("Wrong login credentials!", 400);
            response.setStatus(400);
            gson.toJson(failResponse, response.getWriter());
        }
    }

    private boolean checkUsersLoginData(String username, String password) {
        HashMap usersSet = User.usersLoginData();
        if(usersSet.containsKey(username)) {
            return usersSet.get(username).equals(password);
        } else {
            return false;
        }
    }

    private User createAdmin(String id, String username, String password) {
        return new User(id, username, password, Role.ADMIN);
    }

    private User createUser(String id, String username, String password) {
        return new User(id, username, password, Role.USER);
    }

    private ArrayList<Object> combineResponse(User user, Response response) {
        ArrayList<Object> res = new ArrayList<>();
        res.add(user);
        res.add(response);
        return res;
    }

    private String getBase64FromString(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
