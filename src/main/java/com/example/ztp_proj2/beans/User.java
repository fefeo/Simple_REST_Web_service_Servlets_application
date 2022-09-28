package com.example.ztp_proj2.beans;
import com.example.ztp_proj2.servlets.LoginServlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class User {
    public String id;
    public String login;
    public String pass;
    public final Role role;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.role = Role.USER;
    }
    public User(String login, String pass, Role role) {
        this.login = login;
        this.pass = pass;
        this.role = role;
    }
    public User(String id, String login, String pass, Role role) {
        this.id = id;
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public static HashMap usersLoginData(){
        //Przykładowi użytkownicy
        HashMap<String, String> Users = new HashMap<String, String>();
        List<User> users = Arrays.asList(
                new User("czytelnik11","czyt0077"),
                new User("czytelnik12","czesc1234"),
                new User("czytelnik13","omen991"),
                new User("czytelnik14","Siek1122")
        );
        //HashMap Users
        for(User user : users){
            Users.put(user.login, user.pass);
        }
        return Users;
    }

    public String getName() {
        return login;
    }
}