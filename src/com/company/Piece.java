package com.company;

import java.util.List;

public abstract class Piece {

    final int piecePos;
    final PieceColor pieceColor;

    Piece(final int piecePos, final PieceColor pieceColor){
        this.piecePos = piecePos;
        this.pieceColor = pieceColor;
    }

    public PieceColor getPieceColor(){
        return this.pieceColor;
    }

    //abstract method, because all the pieces are going to have their own behaviour
    public abstract List<Move> getMoves(final Board board);

}
