package com.mylibrary;



import java.sql.*;
import java.util.ArrayList;

public class MethodsSQL {
    public static Connection establishConnection(String url, String username, String password) { //estabilishing connection without the try/catch
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //create a statement
    public static Statement createStatement(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            return stmt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // create result with the given query
    public static ResultSet createResult(Statement stmt, String query) {
        try {
            ResultSet res = stmt.executeQuery(query);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //update the database with the given sql statement
    public static void updateDB(Statement stmt, String sql) throws SQLIntegrityConstraintViolationException {
        try {
            stmt.executeUpdate(sql);
        }catch(SQLIntegrityConstraintViolationException e){
            throw new SQLIntegrityConstraintViolationException();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBook(Book someBook, Statement stmt) throws SQLIntegrityConstraintViolationException {
        updateDB(stmt,
                "INSERT INTO books (`ISBN`, `Name`, `Author`, `Year`, `Price`, `Quantity`)"
                        + "VALUES ('" + someBook.getIsbn() + "',"
                        + "'" + someBook.getName() + "',"
                        + "'" + someBook.getAuthor() + "',"
                        + "'" + someBook.getYear() + "',"
                        + "'" + someBook.getPrice() + "',"
                        + "'" + someBook.getQuantity() + "')"
        );

    }
    //escaping the apostrophe problem during database update
    public static String SQLEscape(String pStr ){
        String mStr;
        mStr = pStr.replace( "'" , "''" );
        return mStr;
    }

    public static ArrayList<Book> getBooksList(ResultSet rs) {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            while (rs.next()) {
                books.add(new Book(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getInt(6)
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return books;
    }
}
