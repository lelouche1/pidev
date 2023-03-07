/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
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
public class AjourReservationController implements Initializable {

    @FXML
    private ChoiceBox<String> choice_modele = new ChoiceBox<String>();

    @FXML
    private TextField txt_nom;

    @FXML
    private DatePicker date_deb;

    @FXML
    private DatePicker date_fin;

    @FXML
    private Label label_prix_total;

    @FXML
    private Button btn_validerResrvation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> listModel = FXCollections.observableArrayList("TOTOYA", "NISSAN", "PORSH", "BMW");
        choice_modele.getItems().addAll(listModel);

        /*   IntegerBinding total_lab = new IntegerBinding() {
                {
                    bind(date_deb.valueProperty(),date_fin.valueProperty());
                }
             @Override
             protected int computeValue() {
                 int jours = (int) ChronoUnit.DAYS.between(date_deb.getValue(), date_fin.getValue());
                  int prix = jours * jours;
                 return prix;//To change body of generated methods, choose Tools | Templates.
             }
         };
        
        label_prix_total.textProperty().bind(total_lab.asString()); */
    }

    @FXML
    void btn_validerResrvation(ActionEvent event) {

        Reservation r = new Reservation();
        ServReservation svr = new ServReservation();

        boolean valide = true;
        String erreur = "";
        Data.dateDebut = r.getDate_debut();
        r.setDate_fin(date_fin.getValue());
        Data.dateFin = r.getDate_fin();

        if (txt_nom.getText().isEmpty()) {
            valide = false;
            erreur = "veuillez saisir un nom";
            //   erreur_nom.setText("veuillez remplir ce champ");
        } else if (date_deb.getValue() == null) {
            valide = false;
            erreur = "veuillez saisir une date de debut";
            //   erreurDateDebut.setText("veuillez remplir ce champ");
        } else if (date_fin.getValue() == null) {
            valide = false;
            erreur = "veuillez saisir une date de fin";
            //   erreurDateFins.setText("veuillez remplir ce champ");
        } else if (r.getDate_debut().isAfter(r.getDate_fin())) {
            valide = false;
            erreur = "date de debut doit etre inferieur date de fin";
        }

        r.setNom_user(txt_nom.getText());
        Data.nom_reser = r.getNom_user();
        r.setDate_debut(date_deb.getValue());

        r.setModele(choice_modele.getValue());
        Data.modele = r.getModele();

        Data.prix = Data.prixModele(choice_modele.getValue());

        if (valide == true) {
            long jours = ChronoUnit.DAYS.between(r.getDate_debut(), r.getDate_fin());
            Data.nbre_jours = jours;
            long prix = jours * r.getPrix();
            Data.prix_total = prix;

            if (svr.ajouterReservation(r) != -1) {
                Data.information("notification", "ajout reussit");
                //############################################################################"
                try {
                    // Charger la scène2.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("imprimerRerservation.fxml"));
                    Parent root = loader.load();

                    // Passer les données de nom et prénom à la scène2
                    ImprimerRerservationController modifController = loader.getController();
                    modifController.afficherModif(Data.nom_reser, Data.modele, Data.dateDebut, Data.dateFin, Data.prix,
                            Data.prix_total, Data.nbre_jours);

                    // Afficher la scène2
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) btn_validerResrvation.getScene().getWindow();
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Data.warning("echec", "echec ajout dans la base de donnee");
        }
    }
    //############################################################################"

}
