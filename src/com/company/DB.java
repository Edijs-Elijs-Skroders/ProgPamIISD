package com.company;

import java.sql.*;

public class DB {

    private final String url = "jdbc:postgresql://localhost:5432/checkers";
    private final String user = "postgres";
    private final String pw = "checkers";
    static Connection dbCon;
    static Statement statement;


    public void connect() {
        try {
            dbCon = DriverManager.getConnection(url, user, pw);
            System.out.println("Connected to DB");
            statement = dbCon.createStatement();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    static void printItems(){
        String query = "Select * from  \"Move\"";
        //ResultSet rs = statement.executeQuery(query);
    }


}
