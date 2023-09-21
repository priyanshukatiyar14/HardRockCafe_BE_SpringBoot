package com.hardrockbe.hardrock.model;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private int password;
    @Column(name="email")
    private String email;
    

    public auth() {
    }

    public auth(String name, String username) {
        this.name = name;
        this.username = username;
    }

    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}