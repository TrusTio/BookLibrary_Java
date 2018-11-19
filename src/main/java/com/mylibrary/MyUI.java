package com.mylibrary;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.sql.*;
import java.util.ArrayList;


@Theme("mytheme")
public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout mainLayout = new HorizontalLayout(); // the main layout that contains everything
        VerticalLayout addBookLayout = new VerticalLayout(); // layout for the adding book part
        VerticalLayout leftLayout = new VerticalLayout();   // layout for the search book and grid part
        FormLayout searchLayout = new FormLayout();
        leftLayout.addComponent(searchLayout);
        mainLayout.addComponents(leftLayout, addBookLayout);


        // add book layout
        Binder<Book> binder = new Binder<>();

        Label addBookLabel = new Label("Add a book");
        TextField isbn = new TextField("ISBN");
        isbn.setMaxLength(13);
        binder.forField(isbn)
                .withValidator(ValidatorFactory.createStringValidator("isbn"))
                .bind(Book::getIsbn, Book::setIsbn);

        TextField name = new TextField("Name");
        name.setMaxLength(40);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Name must be between 3 and 40 characters", 3, 40))
                .bind(Book::getName, Book::setName);

        TextField author = new TextField("Author");
        author.setMaxLength(40);
        binder.forField(author)
                .withValidator(new StringLengthValidator("Must be between 3 and 40 numbers", 3, 40))
                .bind(Book::getAuthor, Book::setAuthor);

        TextField year = new TextField("Publishing year");
        year.setMaxLength(4);
        binder.forField(year)
                .withValidator(ValidatorFactory.createStringValidator("year"))
                .bind(Book::getYear, Book::setYear);


        TextField price = new TextField("Price");
        binder.forField(price)
                .withValidator(ValidatorFactory.createStringValidator("price"))
                .bind(Book::getTextPrice, Book::setTextPrice);

        TextField quantity = new TextField("Quantity");
        binder.forField(quantity)
                .withValidator(ValidatorFactory.createStringValidator("quantity"))
                .bind(Book::getTextQuantity, Book::setTextQuantity);

        Button addButton = new Button("Add book");

        addBookLayout.addComponents(addBookLabel, isbn, name, author, year, price, quantity, addButton);


        // search book layout
        Label searchLabel = new Label("Search");
        TextField isbnTF = new TextField("ISBN");
        TextField nameTF = new TextField("Name");
        TextField authorTF = new TextField("Author");
        TextField yearTF = new TextField("Publishing year");
        Button searchButton = new Button("Search");

        searchLayout.addComponents(searchLabel, isbnTF, nameTF, authorTF, yearTF, searchButton);

        // SQL connection

        Connection connection = MethodsSQL.establishConnection("jdbc:mysql://localhost:3306/book_library", "root", "root");
        // if(connection!=null) System.out.println("Connection successful");
        Statement stmt = MethodsSQL.createStatement(connection);
        // if(stmt!=null) System.out.println("Statement successful");
        ResultSet res = MethodsSQL.createResult(stmt, "SELECT * FROM books");
        // if(res!=null) System.out.println("ResultSet successful");

        // Grid part
        ArrayList<Book> books = MethodsSQL.getBooksList(res);

        Grid<Book> grid = new Grid<>();
        grid.setItems(books);
        grid.addColumn(Book::getIsbn).setCaption("ISBN");
        grid.addColumn(Book::getName).setCaption("Name");
        grid.addColumn(Book::getAuthor).setCaption("Author");
        grid.addColumn(Book::getYear).setCaption("Year");
        grid.addColumn(Book::getPrice).setCaption("Price");
        grid.addColumn(Book::getQuantity).setCaption("Quantity");
        grid.setWidth("900px");

        leftLayout.addComponent(grid);
        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
