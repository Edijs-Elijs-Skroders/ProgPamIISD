package Checkers;

public class Game {
    int ID;
    boolean isWaiting;
    String playerWhite;
    String playerBlack;
    Player winner;
    
    Game(int ID, boolean isWaiting, String playerWhite, String playerBlack){
        this.ID = ID;
        this.isWaiting = isWaiting;
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;

    }
    public int getID(){
        return this.ID;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

//    public void addOpponent(String opponent) {
//        this.playerBlack = opponent;
//        this.isWaiting = false;
//        Main.opponent = this.playerBlack;
//
//    }
}
