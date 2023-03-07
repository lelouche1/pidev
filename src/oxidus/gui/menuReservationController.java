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
import oxidus.entites.Reservation;
import oxidus.services.ServReservation;

/**
 *
 * @author lelou
 */
public class menuReservationController implements Initializable {
    @FXML
    private TableView<Reservation> tabViewReserv;

    @FXML
    private TableColumn<Reservation, String> col_nom_res;

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
    
    @FXML
    private Button modifierReservation;

    ServReservation servicesRese = new ServReservation();

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

    public void afficherReservations(List<Reservation> reservations) {
        ObservableList<Reservation> listeReservations = FXCollections.observableArrayList(reservations);
        tabViewReserv.setItems(listeReservations);
        col_nom_res.setCellValueFactory(new PropertyValueFactory<Reservation, String>("nom_user"));
        col_date_deb.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("date_debut"));
        col_date_fin.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("date_fin"));
        col_prix_res.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("prix"));
        col_modele.setCellValueFactory(new PropertyValueFactory<Reservation, String>("modele"));
        
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
                String dateFin= "" + reservation.getDate_fin();
                if (reservation.getModele().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match titre
                }
                if (reservation.getNom_user().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (prix.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                } if (dateDeb.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                } if (dateFin.toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
                    //Data.type = tableGeneralCollab.getItems().get(myIndex).getType_collaboration();
                    Reservation rr = new Reservation();
                     ServReservation svc = new ServReservation();
                    rr= svc.recherche(Data.nom_reser,Data.modele);
                     Data.id = rr.getId_reservation();
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
        ServReservation svc = new ServReservation();
        if(svc.supprimerReservation(r)){
            System.out.println("suppression ressuit");
            ServReservation serrese = new ServReservation();
            serrese.createChart(anchPie);
            affichagerTableMenu();
        }else{
            System.out.println("echec suppression");
        }
    }
    
      @FXML
    void modifierReservation(ActionEvent event) {
        
        try {
         // Charger la scène2.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifieReservation.fxml"));
            Parent root = loader.load();

            // Passer les données de nom et prénom à la scène2
            ModifieReservationController modifController = loader.getController();
            Reservation r = new Reservation();
            ServReservation servRes = new ServReservation();
            r.setNom_user(Data.nom_reser);
            r.setModele(Data.modele);
            //on recupere toute les info dune collaboration
            System.out.println("le titre "+Data.nom_reser);
            System.out.println("le type "+Data.modele);
           // r.setDate_debut(LocalDate.MAX);
            Reservation rr= servRes.recherche(Data.nom_reser,Data.modele);
            Data.id = rr.getId_reservation();
          //  Data.nom_reser=r.getNom_user();
         //   Data.email=c.getEmail_user();
            modifController.afficherModif(rr);

            // Afficher la scène2
            Scene scene = new Scene(root);
            Stage stage = (Stage) modifierReservation.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}