package com.example.cgi_assignment.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class MessagesDTO {
    List<String> messages = new ArrayList<>();
    List<String> errors = new ArrayList<>();

    public void addMessage(String message){
        messages.add(message);
    }

    public void addErrors(String errorMessage){
        errors.add(errorMessage);
    }
}



