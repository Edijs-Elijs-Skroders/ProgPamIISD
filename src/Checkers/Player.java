package Checkers;

public class Player {
    String name;
    Piece.Team team;

    Player(String name, Piece.Team team){
        this.name = name;
        this.team = team;
    }

    public Piece.Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }
}
