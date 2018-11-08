package com.mylibrary;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Theme("mytheme")
public class MyUI extends UI {
    //TODO add grid connected to SQL database
    //TODO center some texts/fields
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout mainLayout = new HorizontalLayout(); // the main layout that contains everything
        VerticalLayout addBookLayout = new VerticalLayout(); // layout for the adding book part
        VerticalLayout leftLayout = new VerticalLayout();   // layout for the search book and grid part
        FormLayout searchLayout = new FormLayout();
        leftLayout.addComponent(searchLayout);
        mainLayout.addComponents(leftLayout, addBookLayout);

        // add book layout part
        Label addBookLabel = new Label("Add a book");
        TextField isbn = new TextField("ISBN");
        TextField name = new TextField("Name");
        TextField author = new TextField("Author");
        TextField date = new TextField("Publishing date");
        TextField price = new TextField("Price");
        TextField quantity = new TextField("Quantity");
        Button addButton = new Button("Add book");

        addBookLayout.addComponents(addBookLabel, isbn, name, author, date, price, quantity, addButton);

        // search book layout part
        Label searchLabel = new Label("Search");
        TextField isbnTF = new TextField("ISBN");
        TextField nameTF = new TextField("Name");
        TextField authorTF = new TextField("Author");
        TextField dateTF = new TextField("Publishing date");
        Button searchButton = new Button("Search");

        searchLayout.addComponents(searchLabel, isbnTF, nameTF, authorTF, dateTF, searchButton);

        // SQL connection part

        Connection connection = MethodsSQL.establishConnection("jdbc:mysql://localhost:3306/book_library", "root", "root");
        if(connection!=null) System.out.println("Connection successful");
        Statement stmt = MethodsSQL.createStatement(connection);
        if(stmt!=null) System.out.println("Statement successful");
        ResultSet res = MethodsSQL.createResult(stmt, "SELECT * FROM books");
        if(res!=null) System.out.println("ResultSet successful");
        // Grid part
        ArrayList<Book> books = MethodsSQL.getBooksList(res);

        Grid<Book> grid = new Grid<>();
        grid.setItems(books);
        grid.addColumn(Book::getIsbn).setCaption("ISBN");
        grid.addColumn(Book::getName).setCaption("Name");
        grid.addColumn(Book::getAuthor).setCaption("Author");
        grid.addColumn(Book::getDate).setCaption("Date");
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
