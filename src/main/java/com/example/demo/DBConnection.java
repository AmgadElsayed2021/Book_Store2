package com.example.demo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
        // JDBC driver name and database URL
        final static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
        final static String DB_URL = "jdbc:mariadb://localhost:9898/books";

    // Database credentials
        final static String USER = "root";
        final static String PASS = "password";

    public static Connection initDB() throws SQLException, ClassNotFoundException {
        // start connection
            Class.forName(JDBC_DRIVER);

            return DriverManager.getConnection(DB_URL, USER, PASS);

    }


    }

