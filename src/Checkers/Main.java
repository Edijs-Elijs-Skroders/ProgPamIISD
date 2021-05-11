package Checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application {

    public static String username = "";
    public static int usernameMinLen = 2; //minimal username length.
    public static int usernameMaxLen = 20; //maximum username length. Also set in DB
    final Group tiles = new Group();
    Group pieces = new Group();
    public final Tile[][] board = new Tile[Tile.column][Tile.row];
    MoveType moveType;
    Destination destination;


    Parent createBoard() {
        Pane boardBase = new Pane();
        boardBase.setMinSize(1200, 790);
        boardBase.setStyle("-fx-border-color: black"); //makes a border around the pane
        boardBase.getChildren().addAll(tiles, pieces);

        //creates a new tile for each column in each row. Adds the tile to the group.
        //adds pieces to the tiles.

        int i = 0;
        int tileNr = 0;

        for (int col = 0; col < Tile.column; col++) {
            for (int row = 0; row < Tile.row; row++) {

                tileNr++;
                i = row + col;

                Tile boardTile = new Tile(Tile.calcColor(i), col, row);
                boardTile.setStroke(Color.BLACK);
                board[row][col] = boardTile;
                if(Tile.calcColor(i) == Tile.TileColor.BLACK){
                    if (row < 3 ){
                        Piece pawn = makePiece(Piece.Team.BLACK, col, row);
                        boardTile.setPiece(pawn);
                        pieces.getChildren().add(pawn);
                    }
                    if (row >4){
                        Piece pawn = makePiece(Piece.Team.WHITE, col, row);
                        boardTile.setPiece(pawn);
                        pieces.getChildren().add(pawn);
                    }
                }
                tiles.getChildren().add(boardTile);

//                if(boardTile.getPiece() != null){
//                    System.out.println(i);
//                    System.out.println(tileNr);
//                    System.out.println(boardTile.getPiece().getTeam());  //Enable to check Nr, if Tile has a piece and its Team
//                }
            }
        }
        return boardBase;
    }


    int locationFromPixels(double pixel){
        return (int) (pixel + Tile.size /2) / Tile.size;
    }

    Destination checkDest(Piece piece, int clickX, int clickY){

        int checkX = locationFromPixels(piece.getMoveCoordX());
        int checkY = locationFromPixels(piece.getMoveCoordY());
        int newX = checkX + (clickX -checkX) / 2;
        int newY = checkY + (clickY -checkY) / 2;
        System.out.println("clickX: "+ clickX + " newX: " + newX + "checkX:" + checkX);
        System.out.println("clickY: "+ clickY + " newY: " + newY + "checkY:" + checkY);


        //checks if selected tile has a piece and its color. if it has, returns STAY, which resets the piece pos.
        if(board[clickY][clickX].hasPiece() || board[clickY][clickX].getColor() == Tile.TileColor.WHITE){

            Destination rDest = new Destination(MoveType.STAY);
            System.out.println("returned stay");
            return rDest;

        }


        //if ()/

        if (clickX - checkX == 1 && clickY - checkY == piece.getTeam().movement) {
            return new Destination(MoveType.MOVE);
        }

        return new Destination(MoveType.MOVE,piece);
    }



    Piece makePiece(Piece.Team team, int col, int row){

        Piece piece = new Piece(team,col,row);

        piece.setOnMouseReleased(e ->{

            int checkX = locationFromPixels(piece.getLayoutX());
            System.out.println("checkx: " + checkX); //checks mouse release X
            int checkY = locationFromPixels(piece.getLayoutY());
            System.out.println("checkY: " + checkY);


            int pieceOldX = locationFromPixels(piece.getMoveCoordX());
            int pieceOldY = locationFromPixels(piece.getMoveCoordY());

            if (checkX < 0 || checkY < 0 || checkX >= Tile.row || checkY >= Tile.column) {

                destination = new Destination(MoveType.STAY);
            } else {
                destination = checkDest(piece, checkX, checkY);
            }

            switch (destination.getMoveType()) {

                case STAY:
                    piece.reset();
                    break;

                case MOVE:
                    piece.move(checkX, checkY);
                    board[checkY][checkX].setPiece(piece);
                    board[pieceOldY][pieceOldX].setPiece(null);
                    AudioClip sound = new AudioClip(Objects.requireNonNull(getClass().getResource("/res/audio/MOVE2.wav")).toExternalForm());
                    sound.play();
                    System.out.println("Moved");
                    break;

                case KILL:
                    piece.move(checkX, checkY);
                    board[checkY][checkX].setPiece(piece);
                    board[pieceOldX][pieceOldY].setPiece(null);
                    Piece killed = destination.getPiece();
                    board[locationFromPixels(killed.getMoveCoordX())][locationFromPixels(killed.getMoveCoordY())].setPiece(null);
                    pieces.getChildren().remove(killed);
                    break;

            }
            System.out.println(destination);

        });
        return piece;
    }


    enum MoveType{
        KILL , MOVE, STAY
    }


    public class Destination{

        MoveType moveType;
        Piece piece;

        Piece getPiece(){
            return piece;
        }
        MoveType getMoveType(){
            return moveType;
        }
        Destination(MoveType type){
            this(type, null);
        }
        Destination(MoveType type, Piece piece){
            this.moveType = type;
            this.piece = piece;
        }

        public String toString(){
            return "MoveType: " + moveType + " piece:" + piece;
        }



    }
    public static void main(String[] args) {
        DB checkersDB = new DB();
        checkersDB.connect(); //Connects to database "checkers"
        launch(args);
    }

    private Parent createTest() {
        Pane test = new FlowPane();
        test.setMinSize(200, 200);
        test.setStyle("-fx-border-color: black");


        return test;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createBoard());
        Scene test = new Scene(createTest());

        Image icon = new Image("/res/tick.png");
        stage.getIcons().add(icon);

        stage.setTitle("Kursa darbs");
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setResizable(false);



        stage.show();
    }


}