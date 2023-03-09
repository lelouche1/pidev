/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.io.IOException;
import java.net.URL;
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
import oxidus.services.ServAgence;

/**
 * FXML Controller class
 *
 * @author lelou
 */
public class ModifierAgenceController implements Initializable {

      @FXML
    private TextField nom_agence;

    @FXML
    private TextField ville;

    @FXML
    private TextField adresse;

    @FXML
    private TextField email;

    @FXML
    private TextField nb_vehicul;

    @FXML
    private Button btn_valide;

    @FXML
    private Button btn_annuler;
    
    private int id ;
    
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
         Data.information("notification", "modification annuler");
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
    void btn_valide(ActionEvent event) {
            Agence r = new Agence();
        ServAgence svr = new ServAgence();

        boolean valide = true;
        String erreur = "";

        if (nom_agence.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir un nom";
            //   erreur_nom.setText("veuillez remplir ce champ");
        } else if (adresse.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir une adresse";
            //   erreurDateDebut.setText("veuillez remplir ce champ");
        } else if (ville.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir une ville";
            //   erreurDateFins.setText("veuillez remplir ce champ");
        } else if (email.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez renseigner un email";
        } else if (nb_vehicul.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez renseigner un nombre de voiture";
        }else if (isValidEmail(email.getText())) {
            erreur = "veuillez saisir un email au format valide";
        }
        
        r.setAdresse(adresse.getText());
        r.setEmailAg(email.getText());
        r.setNomAg(nom_agence.getText());
        r.setVilleAgence(ville.getText());
        String nb = nb_vehicul.getText();
        int numberVehic = Integer.parseInt(nb);
        r.setNbreVehicule(numberVehic);
        r.setId_agence(id);
        System.out.println("valeur de lelement "+r);
        if (valide == true) {

            if (svr.modifierAgence(r)) {
                Data.information("notification", "modification reussit");
                 try {
                    // Charger la scène2.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menuAgence.fxml"));
                    Parent root = loader.load();


                    // Afficher la scène2
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) btn_valide.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            } else {
                Data.warning("echec", "echec modification dans la base de donnee");
            }
        }else{
            Data.warning("erreur", erreur);
        }
        //############################################################################"
        
    }
    
     public void afficherModif(Agence a){
        nom_agence.setText(a.getNomAg());
         adresse.setText(a.getAdresse());
        email.setText(a.getEmailAg());
        String nbvehic =""+a.getNbreVehicule();
        nb_vehicul.setText(nbvehic);
        ville.setText(a.getVilleAgence());
        id = a.getId_agence();
    }
     
          private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
}
