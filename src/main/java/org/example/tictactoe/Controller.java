package org.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Label myLabel;
    @FXML
    private TextField player1,player2;


    public void startGame(ActionEvent event) throws Exception{
        if (!player1.getText().isEmpty() && !player2.getText().isEmpty()){
            String text1=player1.getText();
            String text2=player2.getText();

            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/game.fxml"));

            Parent root=fxmlLoader.load();
            GameController controller=fxmlLoader.getController();
            controller.setNames(text1,text2);

            Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
            stage.show();

            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else{
             myLabel.setVisible(true);
        }



    }




}