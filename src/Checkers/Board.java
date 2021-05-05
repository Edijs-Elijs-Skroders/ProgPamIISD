package Checkers;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Board {

    static final Group tiles = new Group();

    static Parent createBoard() {
        Pane boardBase = new Pane();
        boardBase.setMinSize(1200, 1000);
        boardBase.setStyle("-fx-border-color: black"); //makes a border around the pane

        //creates a new tile for each column in each row. Adds the tile to the group
        int i = 0;
        for (int row = 0; row < Tile.row; row++) {
            for (int col = 0; col < Tile.column; col++) {
                i = row + col;
                Tile boardTile = new Tile(Tile.calcColor(i), row, col);
                tiles.getChildren().add(boardTile);
            }
        }
        boardBase.getChildren().add(tiles);
        return boardBase;
    }

    public Tile getTile(int tileNr) {
        return null;
    }

}
