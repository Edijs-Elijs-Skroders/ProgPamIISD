package com.company;

public class TileFull extends Tile {

    final Piece piece;

    TileFull(final int tileNr, Piece piece) {
        super(tileNr);
        this.piece =  piece;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

}

