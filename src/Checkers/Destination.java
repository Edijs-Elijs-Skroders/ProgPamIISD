package Checkers;

public class Destination {

    Main.MoveType moveType;
    Piece piece;

    Piece getPiece(){
        return piece;
    }
    Main.MoveType getMoveType(){
        return moveType;
    }

    Destination(Main.MoveType type, Piece piece){
        this.moveType = type;
        this.piece = piece;
    }
    Destination(Main.MoveType type){
        this(type, null);
    }
    public String toString(){
        return "MoveType: " + moveType + " piece:" + piece;
    }
}

