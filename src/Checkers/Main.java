package Checkers;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    public static String username = "";
    public static int usernameMinLen = 2; //minimal username length.
    public static int usernameMaxLen = 20; //maximum username length. Also set in DB

    public static void main(String[] args) {
        launch(args);

        DB checkersDB = new DB();
        checkersDB.connect(); //Connects to database "checkers"


    }

    private Parent createTest() {
        Pane test = new FlowPane();
        test.setMinSize(200, 200);
        test.setStyle("-fx-border-color: black");


        return test;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(Board.createBoard());
        Scene test = new Scene(createTest());
        stage.setTitle("Kursa darbs");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);


        stage.show();
    }


}