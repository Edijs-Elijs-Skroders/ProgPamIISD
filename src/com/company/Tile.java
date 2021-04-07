package com.company;

public abstract class Tile {

    int tileNr;

    Tile(int tileNr){
        this.tileNr = tileNr;
    }

    public abstract boolean isEmpty();

    public abstract Piece getPiece();
}
