package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String pw = "checkers";

    public Connection connect() {
        Connection DBCon = null;
        try {
            DBCon = DriverManager.getConnection(url, user, pw);
            System.out.println("Connected to DB");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return DBCon;
    }

}
