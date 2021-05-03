package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main {

    public static int column = 8;
    public static int row = 8;
    public static String username = "";
    public static int usernameMinLen = 2; //minimal username length.
    public static int usernameMaxLen = 20; //maximum username length. Also set in DB
    public static Color clr1 = Color.WHITE;
    public static Color clr2 = new Color(74, 96, 134);


    public static void main(String[] args) {

        int i = 0;
        while (username == null || username.length() < usernameMinLen || username.length() > usernameMaxLen)
            if (i ==0) {
                username = JOptionPane.showInputDialog("Choose your name");
                i+=1;
            }
            else{
                username = JOptionPane.showInputDialog("Choose a different name(2-20 characters long)");
            }

        DB checkersDB = new DB();
        checkersDB.connect(); //Connects to database "checkers"

        //Jframe config

        JFrame checkers = new JFrame("Kursa Darbs");
        checkers.setSize(820, 950);
        ImageIcon checkersIcon = new ImageIcon("res/tick.png");
        checkers.setIconImage(checkersIcon.getImage());
        checkers.setResizable(false);

        //board JPanel config with square JPanel configs

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(row, column));
        Color c;

        for (int a = 0; a < row; a++) {
            if (a % 2 == 0) {
                c = clr1;
            } else c = clr2;

            for (int b = 0; b < column; b++) {
                JPanel square = new JPanel();
                square.setBackground(c);
                if (c.equals(clr1)) {
                    c = clr2;
                } else c = clr1;
                board.add(square);
            }
        }

        board.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        board.setBounds(5, 100, 750, 750);

        //Settings JPanel config

        JPanel settings = new JPanel();
        settings.setLayout(new FlowLayout());
        Button newGame = new Button("New Game");
        settings.add(newGame);
        settings.setBounds(0, 0, 100, 100);

        checkers.add(board);
        checkers.add(settings);
        checkers.setVisible(true);
        checkers.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}