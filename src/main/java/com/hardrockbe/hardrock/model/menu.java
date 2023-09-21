package com.hardrockbe.hardrock.model;
import javax.persistence.*;

@Entity
@Table(name = "menu")
public class menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="category")
    private String category;
    @Column(name="price")
    private int price;
    @Column(name="description")
    private String description;
    @Lob
    @Column(name = "image")
    private byte[] image;


    public menu() {
    }

    public menu(String name, String category) {
        this.name = name;
        this.category = category;
    }

    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}