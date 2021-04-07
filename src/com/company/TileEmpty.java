package com.company;

public class TileEmpty extends Tile{

    Piece Piece;

    TileEmpty(int tileNr) {
        super(tileNr);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }


    @Override
    public Piece getPiece() {
        return null;  //returns null, because there is no piece on a empty tile
    }
}
