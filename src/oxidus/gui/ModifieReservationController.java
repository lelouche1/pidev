/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oxidus.entites.Reservation;
import oxidus.services.ServReservation;

/**
 * FXML Controller class
 *
 * @author lelou
 */
public class ModifieReservationController implements Initializable {

    @FXML
    private Button btn_validerResrvation;

    @FXML
    private Button annuler_modif;

    @FXML
    private TextField txt_nom;

    @FXML
    private ChoiceBox<String> choice_modele = new ChoiceBox<String>();

    @FXML
    private DatePicker date_deb;

    @FXML
    private DatePicker date_fin;

    @FXML
    private TextField email_client;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> listModel = FXCollections.observableArrayList("TOTOYA", "NISSAN", "PORSH", "BMW");
        choice_modele.getItems().addAll(listModel);
    }

    @FXML
    void annuler_modif(ActionEvent event) {

    }

    @FXML
    void btn_validerResrvation(ActionEvent event) {
        
        boolean valide = true;
        Reservation r = new Reservation();
        ServReservation svr = new ServReservation();
        String erreur = "";
        r.setDate_debut(date_deb.getValue());

        r.setDate_fin(date_fin.getValue());

        if (txt_nom.getText().isEmpty()) {
            valide = false;
            erreur="veuillez saisir un nom";
         //   erreur_nom.setText("veuillez remplir ce champ");
        } else if (date_deb.getValue() == null) {
            valide = false;
            erreur="veuillez saisir une date de debut";
         //   erreurDateDebut.setText("veuillez remplir ce champ");
        } else if (date_fin.getValue() == null) {
            valide = false;
            erreur="veuillez saisir une date de fin";
         //   erreurDateFins.setText("veuillez remplir ce champ");
        } else if (r.getDate_debut().isAfter(r.getDate_fin())) {
            valide = false;
            erreur="date de debut doit etre inferieur date de fin";
        }

        if (valide == true) {
            r.setNom_user(txt_nom.getText());
            r.setModele(choice_modele.getValue());
            //email_client

            if (r.getDate_debut().isBefore(r.getDate_fin())) {
                long jours = ChronoUnit.DAYS.between(r.getDate_debut(), r.getDate_fin());
                Data.nbre_jours = jours;
                long prix = jours * r.getPrix();
                Data.prix_total = prix;

                Reservation rr = svr.recherche(Data.nom_reser, Data.modele);
                r.setId_reservation(rr.getId_reservation());
                if (svr.modifierReservation(r)) {
                    Data.information("notification", "modification reussit");
                    try {
                        // Charger la scène2.fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuReservation.fxml"));
                        Parent root = loader.load();

                        // Afficher la scène2
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) btn_validerResrvation.getScene().getWindow();
                        stage.setScene(scene);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } 
                }else {
            Data.warning("echec", "echec modification dans la base de donnee");
                }
            }

        } else{
            Data.warning("erreur champ",erreur);
        }

    }

    public void afficherModif(Reservation r) {
        txt_nom.setText(r.getNom_user());
        date_deb.setValue(r.getDate_debut());
        date_fin.setValue(r.getDate_fin());
        choice_modele.setValue(r.getModele());
        //
    }

}
