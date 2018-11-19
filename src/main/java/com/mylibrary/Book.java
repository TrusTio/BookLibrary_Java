package com.mylibrary;

public class Book {
    private String isbn, name, author, year;
    private double price;
    private int quantity;

    public Book(String isbn, String name, String author, String year, double price, int quantity) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
    }
    public Book(String isbn, String name, String author, String year, String price, String quantity) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = Double.parseDouble(price);
        this.quantity = Integer.parseInt(quantity);
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

    public String getYear() {
        return year;
    }

    public double getPrice() {
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

    public void setYear(String year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //can't use the int getters/setters on a text field binder
    public String getTextQuantity() {
        return String.valueOf(this.quantity);
    }

    public void setTextQuantity(String textQuantity) {
        this.quantity = Integer.parseInt(textQuantity);
    }

    public String getTextPrice() {
        return String.valueOf(this.price);
    }

    public void setTextPrice(String textPrice) {
        this.price = Double.parseDouble(textPrice);
    }
}
