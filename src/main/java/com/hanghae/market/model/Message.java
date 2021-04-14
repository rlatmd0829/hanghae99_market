package com.hanghae.market.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {

    private String message;

    public Message(String message){
        this.message = message;
    }
}
