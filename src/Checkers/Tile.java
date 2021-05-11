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
    Piece piece;

    Tile(TileColor color, int height, int width) {
        setWidth(size);
        setHeight(size);
        relocate(height * size, width * size);

        if (color == TileColor.WHITE) {
            setFill(clr1);
        } else {
            setFill(clr2);
        }
        this.color = color;
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


    //TileColor, because javafx.scene.paint.Color problems
    public enum TileColor {
        WHITE,
        BLACK
    }


//    //Creates a method and a map for all possible(64) TileEmpty objects
//    private static Map<Integer, TileEmpty> createTilesEmpty(){
//
//        final Map<Integer, TileEmpty> tileEmptyMap =  new HashMap<>();
//
//        for(int a = 0; a < 64; a++){
//            tileEmptyMap.put(a, new TileEmpty(a,column,row));
//        }
//        return tileEmptyMap;
//    }
//
//    private static final Map<Integer, TileEmpty> tileEmptyMap = createTilesEmpty();
//
//
//    //method that creates tiles and returns either TileEmpty or TileFull objects
//    //Uses the tilesEmptyMap to return the value for tileNr key
//
//    public static Tile createTile(final int tileNr, final Piece piece){
//        if(piece == null){
//            return tileEmptyMap.get(tileNr);
//        }
//        else {
//            return new TileFull(tileNr,piece);
//        }
//    }

}

