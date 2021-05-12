package Checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Main extends Application {

    public static String username = "";
    public static String username2 = "";
    public static int usernameMinLen = 2; //minimal username length.
    public static int usernameMaxLen = 20; //maximum username length. Also set in DB

    final Group tiles = new Group();
    static Group piecesWhite = new Group();
    static Group piecesBlack = new Group();
    public static final Tile[][] board = new Tile[Tile.column][Tile.row];
    //ColorInput colorForWhiteDame = new ColorInput();  //For future looks improvements
    //ColorInput colorForBlackDame = new ColorInput();
    Destination destination;
    static Map<Integer, Tile> tileIntegerMap = new HashMap<>();
    static Boolean isMyMove;
    public static Piece.Team opponentsTeam = Piece.Team.BLACK;
    public static Piece.Team myTeam = Piece.Team.WHITE;
    public static boolean isGameOver;
    static String opponent;
    static Game game;
    static Player playerOne = new Player(username, Piece.Team.WHITE);
    static Player playerTwo = new Player(username2, Piece.Team.BLACK);


    Parent createBoard() throws SQLException {
        Pane boardBase = new Pane();
        boardBase.setMinSize(1200, 800);
        boardBase.setStyle("-fx-border-color: black"); //makes a border around the pane

        Button giveupButton = new Button("Give up");
        giveupButton.relocate(1200,0);
        giveupButton.setPrefSize(150,50);
        TextArea hist = new TextArea();
        Label playerLabel = new Label("White pieces :" + username);
        Label playerLabel2 = new Label("Black pieces: " +username2);
        playerLabel.setPrefSize(200,100);
        playerLabel2.setPrefSize(200,100);
        playerLabel.relocate(900,700);
        playerLabel2.relocate(900,600);
        playerLabel.setStyle("-fx-font-size: 20;-fx-background-color:rgb(74, 96, 134); -fx-border-color: WHITE; -fx-text-fill: White");
        playerLabel2.setStyle("-fx-font-size: 20; -fx-background-color: White; -fx-text-fill: Black");
        playerLabel.setVisible(true);
        playerLabel2.setVisible(true);


        hist.relocate(800,0);
        hist.setPrefSize(400,400);
        hist.setVisible(true);

        giveupButton.setOnMouseClicked(mouseEvent -> {
            giveUp(isMyMove);
        });

        boardBase.getChildren().addAll(tiles, piecesBlack, piecesWhite,giveupButton,hist,playerLabel,playerLabel2);

        //creates a new tile for each column in each row. Adds the tile to the group.
        //adds pieces to the tiles.

        int i = 0;
        Integer tileNr = 0;

        for (int col = 0; col < Tile.column; col++) {
            for (int row = 0; row < Tile.row; row++) {

                tileNr++;
                i = row + col;

                Tile boardTile = new Tile(Tile.calcColor(i), col, row, tileNr);
                boardTile.setStroke(Color.BLACK);
                //careful. row != Tile.row.  board[Tile.col][Tile.row] elsewhere
                board[row][col] = boardTile;
                tileIntegerMap.put(tileNr, boardTile);
                if(Tile.calcColor(i) == Tile.TileColor.BLACK){
                    if (row < 3 ){
                        Piece pawn = makePiece(Piece.Team.BLACK, col, row, Piece.Type.PAWN, tileNr);
                        boardTile.setPiece(pawn);
                        piecesBlack.getChildren().add(pawn);
                    }
                    if (row >4){
                        Piece pawn = makePiece(Piece.Team.WHITE, col, row, Piece.Type.PAWN, tileNr);
                        boardTile.setPiece(pawn);
                        piecesWhite.getChildren().add(pawn);
                    }
                }
                tiles.getChildren().add(boardTile);

                System.out.println("TileNr:" + boardTile.getTileNr() +" and tile "+tileNr);
                //System.out.println(tileIntegerMap.get(tileNr).getPiece());
            }
        }
        return boardBase;
    }

    //transforms input pixels to a x or y location
    int locationFromPixels(double pixel){
        return (int) (pixel + Tile.size /2) / Tile.size;
    }

    //checks the piece's desired destination and applies rules to check if its possible
    Destination checkDest(Piece piece, int clickX, int clickY){

        int startX = locationFromPixels(piece.getMoveCoordX());
        int startY = locationFromPixels(piece.getMoveCoordY());
        int newX = startX + (clickX -startX) / 2;
        int newY = startY + (clickY -startY) / 2;
        //outprint for testing
        System.out.println("checkDest clickX: "+ clickX + " newX: " + newX + " startX:" + startX);
        System.out.println("clickY: "+ clickY + " newY: " + newY + " startY:" + startY);
        System.out.println("later PosOnBoard: "+ piece.getPosOnBoard());


        //A RULE FOR ALL PIECES

        //checks if selected tile has a piece and its color. if it has, returns STAY, which resets the piece pos.
        if(board[clickY][clickX].hasPiece() || board[clickY][clickX].getColor() == Tile.TileColor.WHITE){

            Destination rDest = new Destination(MoveType.STAY);
            //System.out.println("returned stay");
            return rDest;

        }

        //RULESET for PAWNS
        if (piece.getType() == Piece.Type.PAWN){
            //X difference for pawns for a move can only always be 1.
            if (clickX - startX == 1 || clickX - startX == -1 ){
                //Same for Y, only now the movement direction needs to be checked.
                if(clickY - startY == piece.getTeam().movement){
                    return new Destination(MoveType.MOVE);
                }
            }
            if (clickX - startX == 2 || clickX - startX == -2 ){
                if(board[newY][newX].getPiece() != null &&clickY - startY == piece.getTeam().movement *2 && piece.getTeam() != board[newY][newX].getPiece().getTeam()){
                    return new Destination(MoveType.KILL, board[newY][newX].getPiece());
                }
            }
        }

        //RULESET FOR DAMES
        if(piece.getType() == Piece.Type.DAME){
            if (clickX - startX == 1 || clickX - startX == -1 ){
                return new Destination(MoveType.MOVE);

            }
            if (clickX - startX == 2 || clickX - startX == -2 ){
                if(board[newY][newX].getPiece() != null && piece.getTeam() != board[newY][newX].getPiece().getTeam()){
                    return new Destination(MoveType.KILL, board[newY][newX].getPiece());
                }
            }
        }
        return new Destination(MoveType.STAY);
    }

    Piece makePiece(Piece.Team team, int col, int row, Piece.Type type, int posOnTile){

        Piece piece = new Piece(team,col,row,type, posOnTile);

        piece.setOnMouseReleased(e ->{
            //DB.checkIfMyMove();
            int checkX = locationFromPixels(piece.getLayoutX());
            int checkY = locationFromPixels(piece.getLayoutY());

            System.out.println("makePiece checkx: " + checkX +" checkY: " + checkY);

            int pieceOldX = locationFromPixels(piece.getMoveCoordX());
            int pieceOldY = locationFromPixels(piece.getMoveCoordY());

            if(isMyMove && piece.getTeam() == opponentsTeam){
                piece.move(pieceOldX, pieceOldY);
            }
            else if(!isMyMove && piece.getTeam() == myTeam){
                piece.move(pieceOldX, pieceOldY);
            }
            else {
                System.out.println("PieceOldX:" + pieceOldX + " PieceOldY:" + pieceOldY);

                //checks location of input, so pieces dont go out of bounds and create exceptions
                if (checkX < 0 || checkY < 0 || checkX >= Tile.row || checkY >= Tile.column) {
                    destination = new Destination(MoveType.STAY);
                } else {
                    destination = checkDest(piece, checkX, checkY);
                }
                switch (destination.getMoveType()) {
                    case STAY -> {
                        piece.reset();
                        System.out.println(piece.getPosOnBoard());
                        System.out.println(tileIntegerMap.get(piece.getPosOnBoard()).getPiece());
                        //System.out.println(piece.getType());
                    }
                    case MOVE -> {
                        //movement
                        piece.move(checkX, checkY);
                        board[checkY][checkX].setPiece(piece);
                        board[pieceOldY][pieceOldX].setPiece(null);
                        //Promotion
                        if (piece.getType() == Piece.Type.PAWN && piece.getTeam() == Piece.Team.WHITE && checkY == 0) {
                            piece.setType(Piece.Type.DAME);
                            piece.setStyle("-fx-border-color: YELLOW");

                        }
                        if (piece.getType() == Piece.Type.PAWN && piece.getTeam() == Piece.Team.BLACK && checkY == Tile.row - 1) {
                            piece.setType(Piece.Type.DAME);
                            piece.setStyle("-fx-border-color: YELLOW");

                        }
                        AudioClip soundMove = new AudioClip(Objects.requireNonNull(getClass().getResource("/res/audio/MOVE2.wav")).toExternalForm());
                        soundMove.play();
                        piece.setPosOnBoard(board[checkY][checkX].getTileNr());

                        System.out.println(tileIntegerMap.get(piece.getPosOnBoard()).getPiece());
                        System.out.println(piece.getPosOnBoard());
                        System.out.println("Moved" + piece.getType());

                        DB.saveMove(piece.getTeam().toString(),piece.getType().toString(), game.getID(), pieceOldX,pieceOldY,checkX,checkY);

                        checkForWin();

                    }
                    case KILL -> {
                        //Movement
                        piece.move(checkX, checkY);
                        board[checkY][checkX].setPiece(piece);
                        //Kill
                        board[pieceOldY][pieceOldX].setPiece(null);
                        Piece killed = destination.getPiece();
                        //System.out.println(destination.getPiece());
                        //System.out.println("Killed X: " + killed.getMoveCoordX() + " and y" + killed.getMoveCoordY());
                        board[locationFromPixels(killed.getMoveCoordY())][locationFromPixels(killed.getMoveCoordX())].setPiece(null);
                        if(killed.getTeam() == Piece.Team.WHITE){
                            piecesWhite.getChildren().remove(killed);
                        }
                        else piecesBlack.getChildren().remove(killed);

                        AudioClip soundKill = new AudioClip(Objects.requireNonNull(getClass().getResource("/res/audio/KILL.wav")).toExternalForm());
                        soundKill.play();

                        //Promotion
                        if (piece.getTeam() == Piece.Team.WHITE && checkY == 0 && piece.getType() == Piece.Type.PAWN) {
                            piece.setType(Piece.Type.DAME);
                            piece.setStyle("-fx-border-color: YELLOW");

                        }
                        if (piece.getType() == Piece.Type.PAWN && piece.getTeam() == Piece.Team.BLACK && checkY == Tile.row - 1) {
                            piece.setType(Piece.Type.DAME);
                            piece.setStyle("-fx-border-color: YELLOW");

                        }
                        //System.out.println(piece.getType());
                        piece.setPosOnBoard(board[checkY][checkX].getTileNr());

                        System.out.println(piece.getPosOnBoard());
                        System.out.println(tileIntegerMap.get(piece.getPosOnBoard()).getPiece());
                        System.out.println("Killed");

                        DB.saveMove(piece.getTeam().toString(),piece.getType().toString(), game.getID(), pieceOldX,pieceOldY,checkX,checkY);

                        checkForWin();
                    }
                }
            }
        });
        return piece;
    }

    enum MoveType{
        KILL , MOVE, STAY
    }

    void giveUp(boolean isMyMove) {
        //pass to give up
        if (isMyMove) {
            isGameOver = true;
            Main.game.setWinner(playerTwo);
            System.out.println("Congrats Black!");

        }
        if (!isMyMove) {
            isGameOver = true;
            Main.game.setWinner(playerOne);
            System.out.println("Congrats White!");
        }
    }

    void checkForWin(){

        if (piecesBlack.getChildren().size() == 0){
            System.out.println("Congrats White!");
            isGameOver = true;
            Main.game.setWinner(playerOne);
        }

        if(piecesWhite.getChildren().size() == 0){
            System.out.println("Congrats Black!");
            isGameOver = true;
            Main.game.setWinner(playerOne);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene checkers = new Scene(createBoard());

        Image icon = new Image("/res/tick.png");
        stage.getIcons().add(icon);

        stage.setTitle("Kursa darbs");
        stage.setScene(checkers);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        int i = 0;
        while (username == null || username.length() < usernameMinLen || username.length() > usernameMaxLen){
            username = JOptionPane.showInputDialog("Choose your name");
        }
        while(username2 == null || username2.length() < usernameMinLen || username2.length() > usernameMaxLen)
        username2 = JOptionPane.showInputDialog("Player 2 username:");
        DB checkersDB = new DB();
         //Connects to database "checkers"
        checkersDB.connect();
        myTeam = Piece.Team.WHITE; //team for first player that connects
        opponentsTeam = Piece.Team.BLACK; //team for second
        DB.createGame(username,username2);
        Game g = DB.getGame();
        DB.checkLastMove();

        DB.getGame();
        launch(args);
    }
}