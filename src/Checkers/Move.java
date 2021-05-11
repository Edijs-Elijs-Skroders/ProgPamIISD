package Checkers;

import javafx.scene.layout.StackPane;

public class Move extends StackPane {
    public double moveCoordX , moveCoordXOld;
    public double moveCoordY, moveCoordYOld;

    void move(double clickCoordY, double clickCoordX){
        moveCoordY = clickCoordY * Tile.size;
        moveCoordX =clickCoordX * Tile.size;
        relocate(moveCoordX, moveCoordY);

    }


}
