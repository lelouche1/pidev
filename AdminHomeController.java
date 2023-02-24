/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Admin;
import entity.Category;
import entity.agence;
import entity.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import khadamni.service.AdminService;
import khadamni.service.CategoryService;
import khadamni.service.FreelancerService;
import khadamni.service.UtilisateurService;
import khadamni.utilisateurfx;

/**
 * FXML Controller class
 *
 * @author lordg
 */
public class AdminHomeController implements Initializable {

    @FXML
    private ListView<Utilisateur> utilisateur;
    @FXML
    private TextField recherche;
    private int id;
    private boolean visible = true;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField num;

    @FXML
    private TextField adresse;
    @FXML
    private TextField mdp;
    @FXML
    private VBox vbox;
    @FXML
    private Text nomt;
    @FXML
    private Text prenomt;
    @FXML
    private Text numt;
    @FXML
    private Text adresset;
    @FXML
    private Text mdpt;
    @FXML
    private Text descriptiont;
    @FXML
    private Text proffessiont;
    @FXML
    private Text verifiedt;
    int i=0;
    @FXML
    private Button ajouter;
    @FXML
    private RadioButton cf;
    @FXML
    private ImageView pdp;
    @FXML
    private Button log;
    @FXML
    private ChoiceBox<String> choix;
    @FXML
    private Button show;
   private String imagePath =null;
    private String c="";


    /**
     * Initializes the controller class.
     */
    public void initializeprem(Admin a){
        ObservableList<String> options = FXCollections.observableArrayList(
    "admin", "utilisateur", "agence");
choix.setItems(options);
 ObservableList<String> option = FXCollections.observableArrayList(
    "true", "false");
 ObservableList<String> o = FXCollections.observableArrayList(
    "true", "false");



AdminService as = new AdminService();
       List<Utilisateur> list = new ArrayList<>();
       list = as.afficherall();
       utilisateur.getItems().clear();
        utilisateur.getItems().addAll(list);
        
        
    }
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void recherche(KeyEvent event) {
    recherche.textProperty().addListener((observable, oldValue, newValue) -> {
        // exécuter la recherche
        UtilisateurService us = new UtilisateurService();
        Utilisateur u = us.findById(Integer.parseInt(newValue));
        FreelancerService fs = new FreelancerService();
        agence f = fs.findById(Integer.parseInt(newValue));
        AdminService as = new AdminService();
        Admin a = as.findById(Integer.parseInt(newValue));
        List<Utilisateur> results = new ArrayList<>();
        if(u!=null){
           results.add(u); 
        }
        if(f!=null){
            results.add(f);
        }
        if(a!=null){
          results.add(a);  
        }
        

        // mettre à jour la liste de résultats
        utilisateur.getItems().clear();
        utilisateur.getItems().addAll(results);
    });
}
    public void setid(int data){
        this.id=data;
    }

    @FXML
    private Utilisateur getuser(MouseEvent event) {
       
    Utilisateur u = utilisateur.getSelectionModel().getSelectedItem();
    UtilisateurService us = new UtilisateurService();
     agence f =null;
        nom.setText(u.getNom());
          prenom.setText(u.getPrenom());
          num.setText(String.valueOf(u.getNumtel()));
          mdp.setText(u.getAdresse());
          adresse.setText(u.getAdresse());
        return u;
            }

    @FXML
    private void supp(ActionEvent event) {
       Utilisateur u = utilisateur.getSelectionModel().getSelectedItem();
       UtilisateurService us = new UtilisateurService();
       FreelancerService fs =new FreelancerService();
    if (u == null) {
        // No user selected, do nothing
        return;
    }
    us.supprimer(u.getId());
     
      
    
   nom.setText("");
          prenom.setText("");
          num.setText("");
          mdp.setText("");
          adresse.setText("");
             utilisateur.refresh();
    utilisateur.refresh();
    }

    @FXML
    private void modif(ActionEvent event) {
         Utilisateur u = utilisateur.getSelectionModel().getSelectedItem();
    UtilisateurService us = new UtilisateurService();
    FreelancerService fs =new FreelancerService();
     if (u == null) {
        // No user selected, do nothing
        return;
    }
      us.modifier(nom.getText(), u.getId());
      nom.setText("");
          prenom.setText("");
          num.setText("");
          mdp.setText("");
          adresse.setText("");
       utilisateur.refresh();
    }

    @FXML
    private void ajout(ActionEvent event) {

      Alert alert = new Alert(Alert.AlertType.INFORMATION);
    visible = true;
        CategoryService cs =new CategoryService();
        List<Category> categories = new ArrayList<>();
     categories = cs.afficher();
     
     
    try {
        if(num.getText() != null) {
            // Vérifier que le nom ne dépasse pas 20 caractères
            if (nom.getText().length() > 20) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nom ne doit pas dépasser 20 caractères.");
                alert.show();
                return;
            }
            if (prenom.getText().length() > 20) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nom ne doit pas dépasser 20 caractères.");
                alert.show();
                return;
            }
            // Vérifier que le numéro de téléphone est composé de 8 chiffres
            if (!num.getText().matches("\\d{8}")) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le numéro de téléphone doit être composé de 8 chiffres.");
                alert.show();
                return;
            }
            
            // Vérifier que le mot de passe comporte au moins 8 caractères
            if (mdp.getText().length() < 8) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le mot de passe doit comporter au moins 8 caractères.");
                alert.show();
                return;
            }
            
            
            Utilisateur u = new Utilisateur(Integer.parseInt(num.getText()), nom.getText(), prenom.getText(), adresse.getText(), "user", "00000");
            UtilisateurService us = new UtilisateurService();
            FreelancerService fs = new FreelancerService();
             
            
            agence f = null;

                us.ajouter(u);
                
                alert.setTitle("Ajout d'utilisateur");
                alert.setHeaderText(null);
                alert.setContentText("Utilisateur ajouté.");
                alert.show();

        }
    } catch (Exception e) {
        e.printStackTrace();
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur est survenue lors de l'ajout de l'utilisateur.");
        alert.show();
    }
    
    nom.setText("");
    prenom.setText("");
    num.setText("");
    mdp.setText("");
    adresse.setText(""); 

    utilisateur.refresh();
    }

    @FXML
    private void logout(ActionEvent event) {
         AdminService us = new AdminService();
        us.logout(id);
        //rja3t lil login   
        try {

            Parent page1
                    = FXMLLoader.load(getClass().getResource("/gui/session.fxml"
                    ));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(utilisateurfx.class.getName()).log(Level.SEVERE,
                    null, ex);

        }
    }
        
    

    @FXML
    private void show(ActionEvent event) {
        
        this.c=choix.getValue().toString();
        AdminService as = new AdminService();
        
        List<Utilisateur> l1 = new ArrayList<>();
         List<agence> l2 = new ArrayList<>();
          List<Admin> l3 = new ArrayList<>();
        if(c=="utilisateur"){
            l1 = as.getAlluser();
             utilisateur.getItems().clear();
        utilisateur.getItems().addAll(l1);
        }
        if(c=="freelancer"){
            l2 = as.getAllFreelancer();
             utilisateur.getItems().clear();
        utilisateur.getItems().addAll(l2);
        }
        if(c=="admin"){
            l3 = as.afficher();
             utilisateur.getItems().clear();
        utilisateur.getItems().addAll(l3);
        }
        
       
    }
}
    
    
