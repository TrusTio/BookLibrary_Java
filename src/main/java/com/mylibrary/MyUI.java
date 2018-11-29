package com.mylibrary;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.dialogs.ConfirmDialog;


import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Theme("mytheme")
public class MyUI extends UI {

    private static ListDataProvider<Book> dataProvider; // let's me update the grid's content without refresh this way

    private static void updateData(ArrayList<Book> books) {
        dataProvider = new ListDataProvider<>(books);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout mainLayout = new HorizontalLayout(); // the main layout that contains everything
        VerticalLayout bookGridLayout = new VerticalLayout();  // layout for grid part
        VerticalLayout addBookLayout = new VerticalLayout(); // layout for the adding book part
        mainLayout.addComponents(bookGridLayout, addBookLayout);


        //connecting to the SQL database
        Connection connection = MethodsSQL.establishConnection("jdbc:mysql://localhost:3306/book_library", "root", "root");
        // if(connection!=null) System.out.println("Connection successful");
        Statement statementSQL = MethodsSQL.createStatement(connection);
        // if(statementSQL!=null) System.out.println("Statement successful");
        ResultSet resultSetSQL = MethodsSQL.createResult(statementSQL, "SELECT * FROM books"); // result set for the grid below
        // if(resultSetSQL!=null) System.out.println("ResultSet successful");

        // Grid part

        ArrayList<Book> books = MethodsSQL.getBooksList(resultSetSQL);
        Grid<Book> bookGrid = new Grid<>();
        bookGrid.setWidth("1000px");
        updateData(books);
        bookGrid.setDataProvider(dataProvider);

        List<ValueProvider<Book, String>> valueProviders = new ArrayList<>();
        valueProviders.add(Book::getIsbn);
        valueProviders.add(Book::getName);
        valueProviders.add(Book::getAuthor);
        valueProviders.add(Book::getYear);
        valueProviders.add(Book::getTextPrice);
        valueProviders.add(Book::getTextQuantity);

        Iterator<ValueProvider<Book, String>> iterator = valueProviders
                .iterator();
        bookGrid.addColumn(iterator.next()).setCaption("ISBN").setWidth(150);
        bookGrid.addColumn(iterator.next()).setCaption("Name");
        bookGrid.addColumn(iterator.next()).setCaption("Author");
        bookGrid.addColumn(iterator.next()).setCaption("Year").setWidth(80);
        bookGrid.addColumn(iterator.next()).setCaption("Price").setWidth(110);
        bookGrid.addColumn(iterator.next()).setCaption("Quantity").setWidth(80);

        HeaderRow filterRow = bookGrid.appendHeaderRow();

        Iterator<ValueProvider<Book, String>> iterator2 = valueProviders
                .iterator();

        bookGrid.getColumns().forEach(column -> {
            TextField field = new TextField();
            ValueProvider<Book, String> valueProvider = iterator2.next();

            field.addValueChangeListener(event -> dataProvider
                    .addFilter(book -> StringUtils.containsIgnoreCase(
                            valueProvider.apply(book), field.getValue())));

            field.setValueChangeMode(ValueChangeMode.EAGER);

            filterRow.getCell(column).setComponent(field);
            field.setSizeFull();
            field.setPlaceholder("Filter");

        });

        bookGrid.addComponentColumn(book -> {
            Button button = new Button("Delete");
            button.addClickListener(click ->
                    ConfirmDialog.show(this, "Please Confirm:", "Are you really sure you want to delete this entry?\n"+ book.getBookInfo(),
                            "Yes", "No", (ConfirmDialog.Listener) dialog -> {
                                if (dialog.isConfirmed()) {
                                    // Confirmed to continue
                                    MethodsSQL.deleteBook(book.getIsbn(),statementSQL);
                                    Notification.show("Successfully deleted.");
                                }
                            })

            );

            return button;
        }).setWidth(90);

        bookGridLayout.addComponent(bookGrid);


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
                .withValidator(ValidatorFactory.createStringValidator("name"))
                .bind(Book::getName, Book::setName);

        TextField author = new TextField("Author");
        author.setMaxLength(40);
        binder.forField(author)
                .withValidator(ValidatorFactory.createStringValidator("author"))
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
        addButton.addClickListener(clickEvent -> {
                    if (connection != null) { // showing error if there is no connection to the database when pressed
                        if (ValidatorFactory.validFields()) { //checks if the fields are valid
                            try {
                                MethodsSQL.addBook(
                                        new Book(
                                                isbn.getValue(),
                                                MethodsSQL.SQLEscape(name.getValue()),
                                                MethodsSQL.SQLEscape(author.getValue()),
                                                year.getValue(),
                                                price.getValue(),
                                                quantity.getValue()
                                        ),
                                        statementSQL);
                                //updating the grid content after successfully added book
                                updateData(MethodsSQL.getBooksList(MethodsSQL.createResult(statementSQL, "SELECT * FROM books")));
                                bookGrid.setDataProvider(dataProvider);
                                Notification.show("Book successfully added!");
                            } catch (SQLIntegrityConstraintViolationException e) { //duplicate primary key exception(ISBN)
                                Notification.show("Failed to add book!\nBook with such an ISBN is already in the database!");
                            }
                        } else {
                            Notification.show("Failed to add book!\nOne of the fields doesn't fit the requirements");
                        }

                    } else {
                        Notification.show("Failed to add book!\nNo connection to the database established!");
                    }
                }
        );

        addBookLayout.addComponents(addBookLabel, isbn, name, author, year, price, quantity, addButton);
        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
