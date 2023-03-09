/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AdminMenuAgenceController implements Initializable {

    @FXML
    private TableView<Agence> tabViewReserv = new TableView<Agence>();

    @FXML
    private TableColumn<Agence, String> col_nom_ag;

    @FXML
    private TableColumn<Agence, String> col_ville;

    @FXML
    private TableColumn<Agence, String> col_email;

    @FXML
    private TableColumn<Agence, String> col_adresse;

    @FXML
    private TableColumn<Agence, Integer> col_nb_vehicules;

    @FXML
    private TextField textfiel_recherche;

    @FXML
    private Button btn_supp_res;

    int myIndex;
    
    ServAgence svanAgence ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        svanAgence = new ServAgence();
        afficherAgences(svanAgence.afficherAgence());
    }

    @FXML
    private Button modifierReservation;

    @FXML
    void btn_supp_res(ActionEvent event) {

        Agence a = new Agence();
        System.out.println("id agence "+Data.id_agence);
        a.setId_agence(Data.id_agence);
        ServAgence svc = new ServAgence();
        if(svc.supprimerAgence(a)){
            Data.information("suppression", "suppression reussit");
            afficherAgences(svc.afficherAgence());
        }else{
            Data.warning("echec", "erreur suppression");
        }
    }

    @FXML
    void modifierReservation(ActionEvent event) {
        
        try {
         // Charger la scène2.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierAgence.fxml"));
            Parent root = loader.load();

            // Passer les données de nom et prénom à la scène2
            ModifierAgenceController modifController = loader.getController();
            Agence a = new Agence();
            ServAgence servAg = new ServAgence();
            a.setNomAg(Data.nom_agence);
            a.setAdresse(Data.adresse_ag);
            //on recupere toute les info dune collaboration
         //   System.out.println("le nom "+Data.nom_agence);
        //    System.out.println("l'adresse est  "+Data.adresse_ag);
            Agence aa= servAg.recherche(Data.nom_agence,Data.adresse_ag);
            Data.id_agence = a.getId_agence();
          //  Data.nom_reser=r.getNom_user();
         //   Data.email=c.getEmail_user();
            modifController.afficherModif(aa);

            // Afficher la scène2
            Scene scene = new Scene(root);
            Stage stage = (Stage) modifierReservation.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public void afficherAgences(List<Agence> agences) {
        ObservableList<Agence> listeAgences = FXCollections.observableArrayList(agences);
        tabViewReserv.setItems(listeAgences);
        col_nom_ag.setCellValueFactory(new PropertyValueFactory<Agence, String>("nomAg"));
        col_ville.setCellValueFactory(new PropertyValueFactory<Agence, String>("villeAgence"));
        col_email.setCellValueFactory(new PropertyValueFactory<Agence, String>("emailAg"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<Agence, String>("adresse"));
        col_nb_vehicules.setCellValueFactory(new PropertyValueFactory<Agence, Integer>("nbreVehicule"));

        //****************** recherche *****************************************
        FilteredList<Agence> filterdata = new FilteredList<Agence>(listeAgences, btn_supp_res -> true);

        //2. set filter predicate whenever rhe filter changes.
        textfiel_recherche.textProperty().addListener((obbervable, oldValue, newValue) -> {
            filterdata.setPredicate(agence -> {
                //if filter text is empty, display all collaboration
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //compare first name and last name of all
                String lowerCaseFilter = newValue.toLowerCase();

                String nbVoiture = "" + agence.getNbreVehicule();
                if (agence.getNomAg().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match titre
                }
                if (agence.getAdresse().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (agence.getEmailAg().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (agence.getVilleAgence().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                }
                if (nbVoiture.toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;  //filter match type collaboration
                } else {
                    return false;
                }
            });
        });

        //wrap the filter in sorted list
        SortedList<Agence> sortedData = new SortedList<>(filterdata);

        //4 bind the sorted data with tableView
        sortedData.comparatorProperty().bind(tabViewReserv.comparatorProperty());

        tabViewReserv.setItems(sortedData);

        //affichage dynamique
        tabViewReserv.setRowFactory(tabrow -> {

            TableRow<Agence> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event
                    -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = tabViewReserv.getSelectionModel().getSelectedIndex();
                    /*       id = Integer.parseInt(String.valueOf(tabViewReserv.getItems().get(myIndex).getId_collaboration()));
                    label_descrition.setText(tableGeneralCollab.getItems().get(myIndex).getTitre()); */

                    Data.nom_agence = tabViewReserv.getItems().get(myIndex).getNomAg();
                    Data.adresse_ag = tabViewReserv.getItems().get(myIndex).getAdresse();
                    Data.nbVoiture = tabViewReserv.getItems().get(myIndex).getNbreVehicule();
                    //Data.type = tableGeneralCollab.getItems().get(myIndex).getType_collaboration();
                        Agence aa = new Agence();
                     ServAgence svc = new ServAgence();
                    aa= svc.recherche(Data.nom_agence,Data.adresse_ag);
                     Data.id_agence = aa.getId_agence();
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
}
