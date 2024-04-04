package org.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;


public class Main extends Application {
    @Override
    public void start(Stage stage){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root=fxmlLoader.load();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }

    public static void main(String[] args) {
        launch(args);
    }
}