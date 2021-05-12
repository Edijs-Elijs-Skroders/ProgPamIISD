package Checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile extends Rectangle {

    public static int size = 100; //size of a tile in pixels
    public static int column = 8; //column count
    public static int row = 8;
    public static javafx.scene.paint.Color clr1 = Color.WHITE;  //manage the colors of the rectangles here
    public static javafx.scene.paint.Color clr2 = Color.rgb(74, 96, 134);
    public TileColor color;
    public int tileNr;
    Piece piece;

    Tile(TileColor color, int height, int width, int tileNr) {
        setWidth(size);
        setHeight(size);
        this.tileNr = tileNr;
        relocate(height * size, width * size);

        if (color == TileColor.WHITE) {
            setFill(clr1);
        } else {
            setFill(clr2);
        }
        this.color = color;
    }

    public int getTileNr() {
        return tileNr;
    }

    void setPiece(Piece piece){
        this.piece = piece;
    }
    Piece getPiece(){
        return piece;
    }
    TileColor getColor(){
        return color;
    }

    //Method to calculate and return the appropriate color
    static TileColor calcColor(int sum) {
        if (sum % 2 == 0) {
            return TileColor.WHITE;
        } else return TileColor.BLACK;
    }

    boolean hasPiece(){
        if(piece == null){
            return false;
        }
        else return true;
    }
    void removePiece(){
        this.piece = null;
    }

    //TileColor, because javafx.scene.paint.Color problems
    public enum TileColor {
        WHITE,
        BLACK
    }
}

