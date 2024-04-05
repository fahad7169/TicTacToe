package org.example.tictactoe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private String player1Name;
    private String player2Name;

    @FXML
    private GridPane myGridPane;
    @FXML
    private Label player1Label,player2Label,playerTurnLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button[][] buttons=new Button[3][3];

        Platform.runLater(()->{
            player1Label.setText("Player 1: "+player1Name);
            player2Label.setText("Player 2: "+player2Name);

        });




        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){

                buttons[i][j]=new Button();
                buttons[i][j].setFont(Font.font("Arial",120));
                buttons[i][j].setStyle("-fx-border-color: white !important;");




                buttons[i][j].setPrefSize(250,217);


                myGridPane.add(buttons[i][j],j,i);



            }
        }

    }

    public void setNames(String player1Name, String player2Name){
        this.player1Name=player1Name;
        this.player2Name=player2Name;
    }

}
