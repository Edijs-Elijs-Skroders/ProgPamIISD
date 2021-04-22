package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public abstract class Main implements MouseListener {

    public static int column = 8;
    public static int row = 8;
    public static Color clr1 = Color.WHITE;
    public static Color clr2 = new Color(74, 96, 134);

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {

        DB checkersDB = new DB();
        checkersDB.connect(); //Connects to database "checkers"

        //Jframe config

        JFrame checkers = new JFrame("Kursa Darbs");
        checkers.setSize(820, 950);
        ImageIcon checkersIcon = new ImageIcon("res/tick.png");
        checkers.setIconImage(checkersIcon.getImage());

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