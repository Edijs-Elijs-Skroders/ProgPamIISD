package Checkers;

//import java.util.ArrayList;
//import java.util.List;
//
//public class PiecePawn extends Piece{
//
//    PiecePawn(final int piecePos,final PieceColor pieceColor) {
//        super(piecePos, pieceColor);
//    }
//
//
//    //all possible moves without slaying for the pawn - ascend or descend on the board by 7 or 9
//    private static final int[] movesAll = {7,9,-7,-9};
//
//
//    //Takes the position of a piece and gives it all possible moves from movesAll ^
//    @Override
//    public List<Move> getMoves(Board board) {
//        int movePossible;
//        List<Move> moveList = new ArrayList<Move>();
//        for (int moves : movesAll){
//            movePossible = this.piecePos + moves;
//            final Tile tilePossible = board.getTile(movePossible);
//            if(tilePossible.isEmpty()){
//                moveList.add(new Move());
//            }
//            else{
//                Piece pieceOnTile = tilePossible.getPiece();
//                PieceColor pieceColor = pieceOnTile.getPieceColor();
//
//                if(this.pieceColor != pieceColor){
//                    moveList.add(new Move());
//                }
//            }
//
//        }
//        return moveList;
//    }
//}
