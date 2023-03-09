/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import oxidus.entites.Reservation;
import oxidus.services.Email;
import oxidus.services.ServReservation;

/**
 *
 * @author lelou
 */
public class menuReservationController implements Initializable {

    @FXML
    private TableView<Reservation> tabViewReserv;

    @FXML
    private Button btn_imprimer;

    @FXML
    private TableColumn<Reservation, String> col_nom_res;
    @FXML
    private TableColumn<Reservation, String> col_status;

    @FXML
    private TableColumn<Reservation, LocalDate> col_date_deb;

    @FXML
    private TableColumn<Reservation, LocalDate> col_date_fin;

    @FXML
    private TableColumn<Reservation, Integer> col_prix_res;

    @FXML
    private TableColumn<Reservation, String> col_modele;

    @FXML
    private AnchorPane anchPie;

    @FXML
    private TextField textfiel_recherche;

    ServReservation servicesRese = new ServReservation();
      @FXML
    private Button btn_alleragence;

    // ObservableList<Reservation> listRes;
    @FXML
    private Button btn_supp_res;

    int myIndex;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        servicesRese.createChart(anchPie);
        affichagerTableMenu();
    }

    public void affichagerTableMenu() {
        ServReservation svr = new ServReservation();
        afficherReservations(svr.afficherReservation());

    }
    
    @FXML
    void btn_alleragence(ActionEvent event) {
         try {
                        // Charger la scène2.fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsulterAgence.fxml"));
                        Parent root = loader.load();

                        // Afficher la scène2
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) btn_alleragence.getScene().getWindow();
                        stage.setScene(scene);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } 
    }

    public void afficherReservations(List<Reservation> reservations) {
        ObservableList<Reservation> listeReservations = FXCollections.observableArrayList(reservations);
        tabViewReserv.setItems(listeReservations);
        col_nom_res.setCellValueFactory(new PropertyValueFactory<Reservation, String>("nom_user"));
        col_date_deb.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("date_debut"));
        col_date_fin.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("date_fin"));
        col_prix_res.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("prix"));
        col_modele.setCellValueFactory(new PropertyValueFactory<Reservation, String>("modele"));
        col_status.setCellValueFactory(new PropertyValueFactory<Reservation, String>("status"));

        //****************** recherche *****************************************
        FilteredList<Reservation> filterdata = new FilteredList<Reservation>(listeReservations, btn_supp_res -> true);

        //2. set filter predicate whenever rhe filter changes.
        textfiel_recherche.textProperty().addListener((obbervable, oldValue, newValue) -> {
            filterdata.setPredicate(reservation -> {
                //if filter text is empty, display all collaboration
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //compare first name and last name of all
                String lowerCaseFilter = newValue.toLowerCase();
                String prix = "" + reservation.getPrix();
                String dateDeb = "" + reservation.getDate_debut();
                String dateFin = "" + reservation.getDate_fin();
                if (reservation.getModele().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match titre
                }
                if (reservation.getNom_user().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (reservation.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (prix.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (dateDeb.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (dateFin.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                } else {
                    return false;
                }
            });
        });

        //wrap the filter in sorted list
        SortedList<Reservation> sortedData = new SortedList<>(filterdata);

        //4 bind the sorted data with tableView
        sortedData.comparatorProperty().bind(tabViewReserv.comparatorProperty());

        tabViewReserv.setItems(sortedData);

        //affichage dynamique
        tabViewReserv.setRowFactory(tabrow -> {

            TableRow<Reservation> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event
                    -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = tabViewReserv.getSelectionModel().getSelectedIndex();
                    /*       id = Integer.parseInt(String.valueOf(tabViewReserv.getItems().get(myIndex).getId_collaboration()));
                    label_descrition.setText(tableGeneralCollab.getItems().get(myIndex).getTitre()); */

                    Data.modele = tabViewReserv.getItems().get(myIndex).getModele();
                    Data.nom_reser = tabViewReserv.getItems().get(myIndex).getNom_user();
                    //   String pprix = tabViewReserv.getItems().get(myIndex).getNom_user();
                    //    Data.prix_total = Integer.parseInt(pprix);
                    Reservation rr = new Reservation();
                    ServReservation svc = new ServReservation();
                    rr = svc.recherche(Data.nom_reser, Data.modele);
                    Data.id = rr.getId_reservation();
                    System.out.println("la reservation recuperer \n" + rr);
                    Data.email_User = rr.getEmail_user();
                    Data.dateDebut = rr.getDate_debut();
                    Data.dateFin = rr.getDate_fin();
                    // label_periode.setText(tableGeneralCollab.getItems().get(myIndex).getDescription());
                    /*    label_periode.setText(cc.getDate_sortie().toString());
                    label_descrition.setText(cc.getDescription());
                    label_titre.setText(cc.getTitre());
                    label_email.setText(cc.getEmail_user()); */
                }
            });
            return myRow;
        });
    }

    @FXML
    void btn_supp_res(ActionEvent event) {
        Reservation r = new Reservation();
        r.setId_reservation(Data.id);
        r.setStatus(Data.ValeurStatus[3]);
        ServReservation svc = new ServReservation();
        System.out.println("suppression id = " + Data.id);
        System.out.println("valeur data status = " + Data.ValeurStatus[3]);
        if (svc.modifierStatusReservation(r)) {
            Data.information("Annulation", "Votre Demande d'annulation a été pris en compte");
            ServReservation serrese = new ServReservation();
            //----------- envoie message -----------------
            Email e = new Email();
            try {
                e.envoyer(Data.email_User, LocalDate.now().toString(), r.getNom_user(), "Reservation annuler merci de votre "
                        + "confiance en vres VROM VROM");
            } catch (MessagingException ex) {
                Logger.getLogger(AjourReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            affichagerTableMenu();
        } else {
            System.out.println("echec suppression");
        }
    }

    @FXML
    void btn_imprimer(ActionEvent event) {
        if (Data.nom_reser.isEmpty()) {
            Data.warning("erreur", "veuillez choisir un element a imprimer");
        } else {
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
                Stage stage = (Stage) btn_imprimer.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }

    }

}
