package com.backbase.message.domain;

import javax.persistence.*;

@Entity
@Table(name = "greetings")
public class Greeting {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "message")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
