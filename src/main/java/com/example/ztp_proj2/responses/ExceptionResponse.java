package com.example.ztp_proj2.responses;

public class ExceptionResponse extends Response{
    public ExceptionResponse(String message, int status) {
        super(message, status);
    }

    public ExceptionResponse() {
        super("OK", 200);
    }
}
