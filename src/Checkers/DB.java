package Checkers;

import java.sql.*;

public class DB {

    static Connection dbCon;
    static Statement statement;
    private final String url = "jdbc:postgresql://localhost:5432/checkers";
    private final String user = "postgres";
    private final String pw = "checkers";
    public static int checkTime = 500;


    public void connect() {
        try {
            dbCon = DriverManager.getConnection(url, user, pw);
            System.out.println("Connected to DB");
            statement = dbCon.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    static void printItems(int id) {
        try {
            String query = "Select * from  \"Move\" where \"move_id\" = ?";
            PreparedStatement ps = dbCon.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("move_id");
                String val = rs.getString("destination");
                String col = rs.getString("color");
                System.out.println("#" + id + "Move:" + col + val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addMove(int move_id, String color, String piece, String destination) {
        try {
            String query = "Insert into  \"Move\"(move_id,color,piece,destination) values (?,?,?,?)";
            PreparedStatement ps = dbCon.prepareStatement(query);
            ps.setInt(1, move_id);
            ps.setString(2, color);
            ps.setString(3, piece);
            ps.setString(4, destination);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void saveMove(String teamColor, String piece, int game_id, int old_X, int old_Y, int new_X, int new_Y) {
        String q = "Insert into \"move\" (color,piece,game_id,old_X,old_Y,new_X,new_Y) values('" + teamColor + "','" + piece + "','" + game_id + "'," + old_X + "," + old_Y + "," + new_X + "," + new_Y + ")";
        try {
            Statement s = dbCon.createStatement();
            s.executeQuery(q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static String getMove() throws SQLException {
        String q = "Select (color,piece,new_X,new_Y) from \"move\" order by game_id limit 1";
        Statement s = dbCon.createStatement();
        try {
            ResultSet rs = s.executeQuery(q);
            while (rs.next()){
                String toTextArea = "" + rs.getString("color") + " " + rs.getString("piece") + " to row: " + rs.getString("new_X") + ", col:" + rs.getString("new_Y");
                return toTextArea;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    static void createGame(String username, String username2) {
        try {
            String update = "INSERT into \"game\" (is_waiting, player_1, player_2) values (false ,?,?)";
            PreparedStatement ps = dbCon.prepareStatement(update);
            ps.setString(1,Main.username);
            ps.setString(2,Main.username2);
            System.out.println("Created a new game");
            statement.executeQuery(update);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Game getGame(){
        String q = "Select * from \"game\" order by id desc limit 1";
        try {
            ResultSet set = statement.executeQuery(q);
            while (set.next()) {

                Main.game = new Game(set.getInt("id"), set.getBoolean("is_waiting"), set.getString("player_1"), set.getString("player_2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static void checkIfMyMove(){
        String q = "Select color from \"move\" where game_id=" + Main.game.getID() + " order by id desc limit 1";
        try {
            Statement st = dbCon.createStatement();
            ResultSet set = st.executeQuery(q);
            while (set.next()) {
                if (set.getString("color").equals("WHITE")) {
                    System.out.println("checkIfMyMove  false");
                    Main.isMyMove = false;
                }
                else {
                    Main.isMyMove = true;
                    System.out.println("checkIfMyMove  true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   //some annoying outputs here, could disable "checkifmymove"
    static void checkLastMove() {
        String q = "Select color from \"move\" where game_id=" + Main.game.getID() + " order by id desc limit 1";
        Main.isMyMove = true;
        Thread t = new Thread() {
            public void run() {
                while (!Main.isGameOver) {
                    try {
                        if (!Main.isMyMove) {
                            Thread.sleep(checkTime);
                        }
                        Statement s = dbCon.createStatement();
                        ResultSet set = s.executeQuery(q);
                        while (set.next()) {
                            if (set.getString("color").equals("WHITE")) {
                                System.out.println("checkIfMyMove  false");
                                Main.isMyMove = false;
                            }
                            else {
                                Main.isMyMove = true;
                                System.out.println("checkIfMyMove  true");
                            }
                        }
                        Thread.sleep(checkTime);
                    }
                    catch (Exception e ) {
                        e.printStackTrace();
                    }
                }
            }

        };
        t.start();
    }
}
