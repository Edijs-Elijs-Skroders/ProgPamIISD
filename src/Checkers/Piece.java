package Checkers;

import javafx.scene.effect.Lighting;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends StackPane {

    Team team;
    public static javafx.scene.paint.Color clrForW = Color.IVORY;  //manage the colors of the rectangles here
    public static javafx.scene.paint.Color clrForB = Color.BLACK;
    public double moveCoordX;
    public double moveCoordY;
    public double clickX;
    public double clickY;
    Lighting light = new Lighting();


    Piece(final Team team, int col, int row) {
        this.team = team;

        Circle pawn = new Circle(Tile.size * 0.3);
        if(team == Team.BLACK){
            pawn.setFill(clrForB);
        }
        else pawn.setFill(clrForW);

        pawn.setEffect(light);



        //centers the piece in the square
        setTranslateY((Tile.size - Tile.size * 0.3 *2 ) / 2);
        setTranslateX((Tile.size - Tile.size * 0.3 *2 ) / 2);

        move(col, row);  //Piece relocates to the appropriate tile position

        setOnMousePressed(e ->{
            clickX = e.getSceneX();
            System.out.println("ClickX:" +clickX);
            clickY = e.getSceneY();
            System.out.println("ClickY:" +clickY);
            if(team == Team.BLACK){
                pawn.setStroke(Color.WHITE);
            }
            else pawn.setStroke(Color.BLACK);
            pawn.setStrokeWidth(5);

        });
        setOnMouseDragged(e ->{
            System.out.println(clickX + " " +  clickY);
            relocate(e.getSceneX() - clickX + moveCoordX ,e.getSceneY() - clickY + moveCoordY);
            System.out.println("drag seceneX" + e.getSceneX());
            System.out.println("drag seceneY " + e.getSceneY());

        });
        setOnMouseClicked(e ->{
            pawn.setStrokeWidth(0);

        });
        setOnMouseMoved(e ->{
            pawn.setStrokeWidth(0);

        });


        getChildren().add(pawn);

    }

    void reset() {
        relocate(moveCoordX, moveCoordY);
    }

    void move(double clickCoordX, double clickCoordY){
        moveCoordY = clickCoordY * Tile.size;
        moveCoordX = clickCoordX * Tile.size;
        relocate(moveCoordX,moveCoordY);
    }

    public double getMoveCoordX() {
        return moveCoordX;
    }

    public double getMoveCoordY() {
        return moveCoordY;
    }

    public Team getTeam() {
        return team;
    }



    public enum Team {
        WHITE(-1),
        BLACK(1);

        int movement;

        Team(int movement){
            this.movement = movement;
        }
    }







}
