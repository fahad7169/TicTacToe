package org.example.tictactoe;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private String player1Name;
    private String player2Name;

    @FXML
    private GridPane myGridPane;
    @FXML
    private Label player1Label,player2Label,playerTurnLabel;
    boolean turnX;
    String Turn;
    boolean gameOver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button[][] buttons=new Button[3][3];
        Bloom glow=new Bloom(0.5);







        Platform.runLater(()->{
            player1Label.setText("Player 1: "+player1Name);
            player2Label.setText("Player 2: "+player2Name);


        });
        Platform.runLater(()->{
            Turn=setFirstTurn();
            playerTurnLabel.setText(Turn + "'s Turn");
        });


        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){

                buttons[i][j]=new Button();
                buttons[i][j].setFont(Font.font("Arial",110));
                buttons[i][j].setTextFill(Paint.valueOf(String.valueOf(Color.WHITE)));
//                buttons[i][j].setTextAlignment(TextAlignment.LEFT);

                buttons[i][j].setStyle("-fx-border-color: white !important;");


                buttons[i][j].setPrefSize(250,217);

                buttons[i][j].setEffect(glow);



                myGridPane.add(buttons[i][j],j,i);


                int finalI = i;
                int finalJ=j;
                buttons[i][j].setOnAction(event -> {
                    if (turnX && buttons[finalI][finalJ].getText().isEmpty()){

                        buttons[finalI][finalJ].setText("O");
                        Turn=(Turn.equals(player1Name))?player2Name:player1Name;
                        playerTurnLabel.setText(Turn + "'s Turn");
                    }
                    else if (!turnX && buttons[finalI][finalJ].getText().isEmpty()){
                        buttons[finalI][finalJ].setText("X");
                        Turn=(Turn.equals(player1Name))?player2Name:player1Name;
                        playerTurnLabel.setText(Turn + "'s Turn");

                    }
                    turnX=!turnX;

                });
                //Check if any player has won the game;

                //Horizontal






            }
        }

    }
    public String setFirstTurn(){
        Random random=new Random();
        return (random.nextBoolean())?player1Name:player2Name;

    }

    public void setNames(String player1Name, String player2Name){
        this.player1Name=player1Name;
        this.player2Name=player2Name;
    }

}
