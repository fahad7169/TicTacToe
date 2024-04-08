package org.example.tictactoe;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private String player1Name;
    private String player2Name;
    @FXML private AnchorPane root1;

    @FXML
    private GridPane myGridPane;
    @FXML
    private Label player1Label,player2Label,playerTurnLabel;
    private boolean turnX;
    private String Turn;
    private boolean gameOver;

    private Button[][] buttons;
    private Line line;
    private TranslateTransition translateTransition;
    private Transition length;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons=new Button[3][3];
        Bloom glow=new Bloom(0.5);


        //Horizontal line
        line=new Line();
//        line.setStartX(584);
//        line.setEndX(584);
//        line.setStartY(170);
//        line.setEndY(685);
        line.setStrokeWidth(10);
        line.setStroke(Paint.valueOf(String.valueOf(Color.INDIANRED)));
        line.setEffect(glow);
        line.setBlendMode(BlendMode.DIFFERENCE);
        root1.getChildren().add(line);

        translateTransition=new TranslateTransition(Duration.seconds(1),line);
        translateTransition.setInterpolator(Interpolator.DISCRETE);
        translateTransition.setCycleCount(1);

        length = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                // Increase the length of the line based on the animation progress
                line.setEndY(170 + frac * 515);
            }
        };
//        translateTransition.play();
//        length.play();








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
                    if (!gameOver) {
                        if (turnX && buttons[finalI][finalJ].getText().isEmpty()) {

                            buttons[finalI][finalJ].setText("O");
                            Turn = (Turn.equals(player1Name)) ? player2Name : player1Name;
                            playerTurnLabel.setText(Turn + "'s Turn");
                        } else if (!turnX && buttons[finalI][finalJ].getText().isEmpty()) {
                            buttons[finalI][finalJ].setText("X");
                            Turn = (Turn.equals(player1Name)) ? player2Name : player1Name;
                            playerTurnLabel.setText(Turn + "'s Turn");

                        }
                        turnX = !turnX;


                    }
                    //check for winner
                    if (!gameOver){
                        checkWinner();
                    }


                });




            }


        }

    }
    public void HorizontalLineSet(int yValue){
        line.setStartX(80);
        line.setEndX(620);
        line.setStartY(yValue);
        line.setEndY(yValue);
        Transition length1 = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                // Increase the length of the line based on the animation progress
                line.setEndX(80 + frac * 540);
            }
        };
        translateTransition.play();
        length1.play();

    }
    public void VericalLineSet(int xValue){
        line.setStartX(xValue);
        line.setEndX(xValue);
        line.setStartY(170);
        line.setEndY(685);

        Transition length1 = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                // Increase the length of the line based on the animation progress
                line.setEndY(170 + frac * 515);
            }
        };
        translateTransition.play();
        length1.play();
    }
    public void checkWinner(){
        //Horizontal
        for (int i=0; i<3; i++){
            if (!buttons[i][0].getText().isEmpty() &&
                    buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText())){
                    gameOver=true;
                    if (i==0 ){
                        HorizontalLineSet(210);
                    } else if (i==1) {
                        HorizontalLineSet(425);
                    } else if (i==2){
                        HorizontalLineSet(645);
                    }
                    break;
            }
        }

        //Vertical
        for (int i=0; i<3; i++){
            if (!buttons[0][i].getText().isEmpty() &&
                    buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[0][i].getText().equals(buttons[2][i].getText())){
                gameOver=true;
                if (i==0 ){
                   VericalLineSet(118);
                } else if (i==1) {
                    VericalLineSet(352);
                } else if (i==2){
                     VericalLineSet(584);
                }
                break;
            }
        }

        //Diagonally
        if (!buttons[0][0].getText().isEmpty() && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText())){
            gameOver=true;
        }
        if (!buttons[0][2].getText().isEmpty() && buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText())){
            gameOver=true;
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
