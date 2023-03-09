/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.io.IOException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oxidus.entites.Agence;
import oxidus.entites.Reservation;
import oxidus.services.ServAgence;
import oxidus.services.ServReservation;

/**
 * FXML Controller class
 *
 * @author lelou
 */
public class AjouterAgenceController implements Initializable {

    @FXML
    private TextField txt_nom;

    @FXML
    private Button btn_validerAgence;

    @FXML
    private TextField emailField;

    @FXML
    private Button btn_annuler;

    @FXML
    private TextField adressField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField nbreVoiture;
    
     
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void btn_annuler(ActionEvent event) {
        try {
                    // Charger la scène2.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menuAgence.fxml"));
                    Parent root = loader.load();


                    // Afficher la scène2
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) btn_annuler.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
    }

    @FXML
    void btn_validerAgence(ActionEvent event) {

        Agence r = new Agence();
        ServAgence svr = new ServAgence();

        boolean valide = true;
        String erreur = "";

        if (txt_nom.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir un nom";
            //   erreur_nom.setText("veuillez remplir ce champ");
        } else if (adressField.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir une adresse";
            //   erreurDateDebut.setText("veuillez remplir ce champ");
        } else if (villeField.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir une ville";
            //   erreurDateFins.setText("veuillez remplir ce champ");
        } else if (emailField.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez renseigner un email";
        } else if (nbreVoiture.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez renseigner un nombre de voiture";
        }else if (isValidEmail(emailField.getText())) {
            erreur = "veuillez saisir un email au format valide";
        }
        
        r.setAdresse(adressField.getText());
        r.setEmailAg(emailField.getText());
        r.setNomAg(txt_nom.getText());
        r.setVilleAgence(villeField.getText());
        String nb = nbreVoiture.getText();
        int numberVehic = Integer.parseInt(nb);
        r.setNbreVehicule(numberVehic);
        if (valide == true) {

            if (svr.ajouterAgence(r) != -1) {
                Data.information("notification", "ajout reussit");
                 try {
                    // Charger la scène2.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menuAgence.fxml"));
                    Parent root = loader.load();


                    // Afficher la scène2
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) btn_validerAgence.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            } else {
                Data.warning("echec", "echec ajout dans la base de donnee");
            }
        }else{
            Data.warning("erreur", erreur);
        }
        //############################################################################"        

    }
    
     private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
   
}
