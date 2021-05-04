package com.company;

import jdk.jfr.StackTrace;

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

    static void printItems(int id){
        try {
            String query = "Select * from  \"Move\" where \"move_id\" = ?";
            PreparedStatement ps = dbCon.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("move_id");
                String val = rs.getString("destination");
                String col = rs.getString("color");
                System.out.println("#" + id + "Move:" + col + val);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addMove(int move_id, String color, String piece, String destination){
        try {
            String query = "Insert into  \"Move\"(move_id,color,piece,destination) values (?,?,?,?)";
            PreparedStatement ps = dbCon.prepareStatement(query);
            ps.setInt(1,move_id);
            ps.setString(2,color);
            ps.setString(3,piece);
            ps.setString(4,destination);
            ps.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
