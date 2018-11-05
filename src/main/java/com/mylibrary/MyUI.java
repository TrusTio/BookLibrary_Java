package com.mylibrary;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;


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


        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
