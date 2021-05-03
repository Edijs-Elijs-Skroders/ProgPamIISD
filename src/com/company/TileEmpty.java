package com.company;

public class TileEmpty extends Tile{

    Piece piece;

    TileEmpty(final int tileNr) {
        super(tileNr);
    }

    @Override
    public boolean isEmpty() {
        return true; //true, because TimeEmpty class
    }


    @Override
    public Piece getPiece() {
        return null;  //returns null, because there is no piece on a empty tile
    }
}
