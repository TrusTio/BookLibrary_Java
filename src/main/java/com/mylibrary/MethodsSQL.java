package com.mylibrary;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public static void updateDB(Statement stmt, String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
