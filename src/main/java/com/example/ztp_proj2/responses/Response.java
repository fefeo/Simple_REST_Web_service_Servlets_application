package com.example.ztp_proj2.responses;

public class Response {
    private String message;
    private int status;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
    }
    public String getMessage(){return message;}

    public void setMessage(String newMessage){
        message = newMessage;
    }

    public int getStatus(){return status;}

    public void setStatus(int newStatus){
        status = newStatus;
    }


}
