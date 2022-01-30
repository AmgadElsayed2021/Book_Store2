//package com.example.demo;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ApplicationDao {
//    public List<Books> loadBooks() {
//        List<Books> books = new ArrayList<>();
//        try {
//            Connection connection =DBConnection.initDB();
//            String sql = "select * from titles ";
//
//            sharedBooksMethods(books, connection, sql);
//
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
//        return books;
//    }
//    private void sharedBooksMethods(List<Books> books, Connection connection, String sql) throws SQLException {
//        Books book;
//        Statement statement = connection.createStatement();
//
//        ResultSet set = statement.executeQuery(sql);
//
//        while (set.next()) {
//            book = new Books();
//            book.setISBN(set.getString("isbn"));
//            book.setTitle(set.getString("title"));
//            book.setEdition(set.getInt("editionNumber"));
//            book.setCopyright(set.getString("copyright"));
//            books.add(book);
//
//        }
//    }
//
//}
