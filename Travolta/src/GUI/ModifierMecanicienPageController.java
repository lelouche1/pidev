/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.*;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ModifierMecanicienPageController implements Initializable {

    @FXML
    private TextField NomActiviteLabel;
    @FXML
    private TextField PrixActiviteField;
    @FXML
    private Label PrixTotal;
    @FXML
    private TextField EmplacementImageLabel;
    @FXML
    private Label PrixTotal2;
    @FXML
    private Label PrixTotal1;
    @FXML
    private TextField TypeActiviteField;
    @FXML
    private Button AnnulerAjoutActiviteManually;
    @FXML
    private Button ButtonAjouterActiviteButton;
    @FXML
    private Label error_act;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ToGestionMecanicien(ActionEvent event) {
         Parent root;
         try {
            root = FXMLLoader.load(getClass().getResource("GestionMecanicien.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifierMecanicienPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AjouterMecanicienManually(ActionEvent event) {
        MecanicienService as=new MecanicienService();
        as.update(GestionMecanicienController.IdMecanicien, NomActiviteLabel.getText(), Float.parseFloat(PrixActiviteField.getText()), TypeActiviteField.getText());
   Parent root;
         try {
            root = FXMLLoader.load(getClass().getResource("GestionMecanicien.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ModifierMecanicienPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
