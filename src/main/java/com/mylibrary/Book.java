package com.mylibrary;

public class Book {
    private String isbn, name, author, date;
    private Double price;
    private int quantity;

    public Book(String isbn, String name, String author, String date, Double price, int quantity) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
