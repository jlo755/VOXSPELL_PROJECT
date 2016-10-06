/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author jacky
 */
public class MainMenuController implements Initializable {
    
    @FXML private Label label;
    @FXML private Button newGame;
    @FXML private Button visitShop;
    @FXML private Button exitGame;
    
    @FXML
    private void newGameAction(ActionEvent event){
        new SceneMediator().showScene((Stage)newGame.getScene().getWindow(), "newGame");
    }
    
    @FXML
    private void visitShopAction(ActionEvent event) {
        //label.setText("Hello World!");
    }
    
    @FXML
    private void exitGameAction(ActionEvent event){
       Stage stage = (Stage)newGame.getScene().getWindow();
       stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
}
