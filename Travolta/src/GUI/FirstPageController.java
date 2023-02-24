/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import GUI.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class FirstPageController implements Initializable {

    @FXML
    private Button clientbutton;
    @FXML
    private Button adminbutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ToClientPage(ActionEvent event) throws IOException {
       
        
        
    }

   @FXML
private void ToGestionEntretien(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GestionEntretien.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
