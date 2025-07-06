package com.myoidc.auth_server.models;

import java.util.UUID;

public class Greeting extends DbEntity{

    private String message = "Not defined";

    public Greeting(String message){
        id = String.valueOf(UUID.randomUUID());
        this.message = message;
    }

    public String getId(){
        return id;
    }

    public String getMessage(){
        return this.message;
    }
}
