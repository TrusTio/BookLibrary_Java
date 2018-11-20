package com.mylibrary;

class Book {
    private String isbn, name, author, year;
    private double price;
    private int quantity;

    Book(String isbn, String name, String author, String year, double price, int quantity) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
    }
    Book(String isbn, String name, String author, String year, String price, String quantity) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = Double.parseDouble(price);
        this.quantity = Integer.parseInt(quantity);
    }

    String getIsbn() {
        return isbn;
    }

    String getName() {
        return name;
    }

    String getAuthor() {
        return author;
    }

    String getYear() {
        return year;
    }

    double getPrice() {
        return price;
    }

    int getQuantity() {
        return quantity;
    }

    void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    void setName(String name) {
        this.name = name;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    void setYear(String year) {
        this.year = year;
    }

    void setPrice(double price) {
        this.price = price;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //can't use the int getters/setters on a text field binder
    String getTextQuantity() {
        return String.valueOf(this.quantity);
    }

    void setTextQuantity(String textQuantity) {
        this.quantity = Integer.parseInt(textQuantity);
    }

    String getTextPrice() {
        return String.valueOf(this.price);
    }

    void setTextPrice(String textPrice) {
        this.price = Double.parseDouble(textPrice);
    }
}
