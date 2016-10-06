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
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 *
 * @author jacky
 */
public class NewGameController implements Initializable{
    @FXML private Button back;
    @FXML private Button relisten;

    @FXML
    private void backAction(ActionEvent event){
        new SceneMediator().showScene((Stage)back.getScene().getWindow(), "mainMenu");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //To change body of generated methods, choose Tools | Templates.
    }
    
}
