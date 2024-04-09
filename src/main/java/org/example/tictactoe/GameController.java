package org.example.tictactoe;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.security.Key;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private String player1Name;
    private String player2Name;
    @FXML private Button restartButton;
    @FXML private AnchorPane root1;

    @FXML
    private GridPane myGridPane;
    @FXML
    private Label player1Label,player2Label,playerTurnLabel;
    private boolean turnX;
    private String Turn;
    private boolean gameOver,gameDraw;

    private Button[][] buttons;
    private Line line;
    private TranslateTransition translateTransition;
    @FXML private ProgressIndicator loader;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons=new Button[3][3];
        DropShadow dropShadow=new DropShadow();
        dropShadow.setRadius(15);
        dropShadow.setColor(Color.SKYBLUE);





        //LINE FOR FINISHING GAME
        line=new Line();
        line.setStrokeWidth(7);
        line.setStroke(Paint.valueOf(String.valueOf(Color.SKYBLUE)));
        line.setBlendMode(BlendMode.DIFFERENCE);
        line.setEffect(dropShadow);
        root1.getChildren().add(line);

        translateTransition=new TranslateTransition(Duration.seconds(1),line);
        translateTransition.setInterpolator(Interpolator.DISCRETE);
        translateTransition.setCycleCount(1);


        Platform.runLater(()->{
            player1Label.setText("Player 1: "+player1Name);
            player2Label.setText("Player 2: "+player2Name);
            Turn=setFirstTurn();
            playerTurnLabel.setText(Turn + "'s Turn");

        });


        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){

                buttons[i][j]=new Button();
                buttons[i][j].setFont(Font.font("Arial",110));
                buttons[i][j].setTextFill(Paint.valueOf(String.valueOf(Color.WHITE)));

                buttons[i][j].setStyle("-fx-border-color: white !important;");


                buttons[i][j].setPrefSize(250,217);

                buttons[i][j].setEffect(dropShadow);



                myGridPane.add(buttons[i][j],j,i);


                int finalI = i;
                int finalJ=j;
                buttons[i][j].setOnAction(event -> {
                    if (!gameOver && !gameDraw) {
                        if (turnX && buttons[finalI][finalJ].getText().isEmpty()) {

                            buttons[finalI][finalJ].setText("O");
                            checkWinner();
                            checkDraw();
                            if (!gameOver) {
                                Turn = (Turn.equals(player1Name)) ? player2Name : player1Name;
                                if(!gameDraw){
                                    playerTurnLabel.setText(Turn + "'s Turn");
                                }
                                else {
                                    restartButton.setVisible(true);
                                    playerTurnLabel.setText("Game Draw");
                                }

                            }
                            else {
                                restartButton.setVisible(true);
                                playerTurnLabel.setText(Turn + " is Winner");
                            }

                        } else if (!turnX && buttons[finalI][finalJ].getText().isEmpty()) {
                            buttons[finalI][finalJ].setText("X");
                            checkWinner();
                            checkDraw();
                            if (!gameOver) {
                                Turn = (Turn.equals(player1Name)) ? player2Name : player1Name;
                                if(!gameDraw){
                                    playerTurnLabel.setText(Turn + "'s Turn");
                                }
                                else {
                                    restartButton.setVisible(true);
                                    playerTurnLabel.setText("Game Draw");
                                }
                            }
                            else {
                                restartButton.setVisible(true);
                                playerTurnLabel.setText(Turn + " is Winner");
                            }

                        }
                        turnX = !turnX;


                    }


                });


            }


        }

    }
    private void HorizontalLineSet(int yValue){
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
    private void VerticalLineSet(int xValue){
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
    private void Diagonal1LineSet(){
        line.setStartX(80);
        line.setStartY(170);
        Transition length1 = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                // Increase the length of the line based on the animation progress
                line.setEndX(80 + frac * 540 );
                line.setEndY(170 + frac * 505);
            }
        };
        translateTransition.play();
        length1.play();
    }
    private void Diagonal2LineSet(){
        line.setStartX(615);
        line.setStartY(175);
         Transition length1 = new Transition() {
            {
                setCycleDuration(Duration.seconds(1));
            }

            @Override
            protected void interpolate(double frac) {
                // Increase the length of the line based on the animation progress
                line.setEndX(615 - frac * 530 );
                line.setEndY(175 + frac * 495);
            }
        };
        translateTransition.play();
        length1.play();
    }
    private void checkDraw(){
        if (!buttons[0][0].getText().isEmpty() &&
                !buttons[0][1].getText().isEmpty() &&
                !buttons[0][2].getText().isEmpty() &&
                !buttons[1][0].getText().isEmpty() &&
                !buttons[1][1].getText().isEmpty() &&
                !buttons[1][2].getText().isEmpty() &&
                !buttons[2][0].getText().isEmpty() &&
                !buttons[2][1].getText().isEmpty() &&
                !buttons[2][2].getText().isEmpty()  ){
//            System.out.println("Hey, it's draw");
            gameDraw=true;
        }
    }
    private void checkWinner(){
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
                    } else{
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
                   VerticalLineSet(118);
                } else if (i==1) {
                    VerticalLineSet(352);
                } else{
                     VerticalLineSet(584);
                }
                break;
            }
        }

        //Diagonally
        if (!buttons[0][0].getText().isEmpty() && buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText())){
            gameOver=true;
            Diagonal1LineSet();
        }
        if (!buttons[0][2].getText().isEmpty() && buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText())){
            gameOver=true;
            Diagonal2LineSet();
        }

    }
    private String setFirstTurn(){
        Random random=new Random();
        return (random.nextBoolean())?player1Name:player2Name;

    }

    public void setNames(String player1Name, String player2Name){
        this.player1Name=player1Name;
        this.player2Name=player2Name;
    }

    public void restartGame(ActionEvent event){

        loader.setVisible(true);
        Task<Void> task=new Task<>() {

            @Override
            protected Void call() throws Exception {



                Thread.sleep(200);
                return null;
            }
        };

        task.setOnSucceeded(event2 -> {
            loader.setVisible(false);

            try {
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/game.fxml"));

                Parent root=fxmlLoader.load();
                GameController controller=fxmlLoader.getController();
                controller.setNames(player1Name,player2Name);

                Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();

                Scene scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e){
                System.out.println(e);
            }

        });

        Thread thread=new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

}
