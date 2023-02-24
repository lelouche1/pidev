/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entity.Category;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import khadamni.service.CategoryService;
import khadamni.service.UtilisateurService;
import khadamni.utilisateurfx;

/**
 * FXML Controller class
 *
 * @author lordg
 */
public class inscrireController implements Initializable {

    @FXML
    private Button button_ajouter;
    
    @FXML
    private TextField nom_field;
    @FXML
    private TextField mdp_field;
    @FXML
    private TextField prenom_field;
    @FXML
    private TextField num_field;
    @FXML
    private TextField adresse_field;
    @FXML
    private TextField role_filed;
    
    @FXML
    private VBox bf;
    private  int i=0;


    private static String photo="";
    @FXML
    private Button sortir;
    @FXML
    private VBox ab;

    private boolean b =false;
    @FXML
    private PasswordField confirm;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        bf.setVisible(b);
        ab.setVisible(b);
        ObservableList<String> o = FXCollections.observableArrayList(
    "true", "false");
 List<String> nomsCategories = new ArrayList<>();
 CategoryService cs = new CategoryService();
 List<Category> categories = cs.afficher();
for (Category category : categories) {
    nomsCategories.add(category.getNom());
}

    }    

  

   
   /* private void get_id(InputMethodEvent event) {
        
    }*/

    @FXML
    private void ajouter_personne(ActionEvent event) {
       //ajout user 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       
       if (nom_field.getText().length() > 20) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nom ne doit pas dépasser 20 caractères.");
                alert.show();
                return;
            }
       if (prenom_field.getText().length() > 20) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nom ne doit pas dépasser 20 caractères.");
                alert.show();
                return;
       }
            // Vérifier que le numéro de téléphone est composé de 8 chiffres
        if (!num_field.getText().matches("\\d{8}")) {
                
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le numéro de téléphone doit être composé de 8 chiffres.");
                alert.show();
                return;
                
        }
            
            // Vérifier que le mot de passe comporte au moins 8 caractères
        if (mdp_field.getText().length() < 8) {
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le mot de passe doit comporter au moins 8 caractères.");
                alert.show();
                return;
        }
        if(!confirm.getText().equals(mdp_field.getText())){
           alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("enter le meme mot de passe");
                alert.show();
                return;
       }
            
        if(b==false){
               try {
             UtilisateurService us = new UtilisateurService();
    Utilisateur u = new Utilisateur(Integer.parseInt(num_field.getText()),nom_field.getText(),prenom_field.getText(),adresse_field.getText(),"user",mdp_field.getText());
      us.ajouter(u);
    

        alert.setTitle("ajout user ");

        alert.setHeaderText(null);

       alert.setContentText("user ajouter ");

      alert.show();
       }catch  (Exception ex) {
           

        alert.setTitle("warning");

        alert.setHeaderText(null);

       alert.setContentText("echoué ");

      alert.show();
       
        } 
        }
        //ajout freelancer
       /* if(b){
            try {
               FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(null);
                String imagePath = selectedFile.getAbsolutePath();
            FreelancerService fs = new FreelancerService();
        Freelancer f1 = new Freelancer(1,Integer.parseInt(num_field.getText()), nom_field.getText(), prenom_field.getText(),  adresse_field.getText(), "freelancer", imagePath, role_filed.getText(), 0, prof.getValue().toString(), false,mdp_field.getText());
           fs.ajouter(f1);
           
            alert.setTitle("ajout user ");

        alert.setHeaderText(null);

       alert.setContentText("freelancer ajouté ");

      alert.show();
      i = 0;
       }catch  (Exception ex) {
           

        alert.setTitle("warning");

        alert.setHeaderText(null);

       alert.setContentText("echoué ");

      alert.show();
       
        }
    }*/
    
     //rja3t lil login   
            try {

Parent page1 =
FXMLLoader.load(getClass().getResource("/gui/session.fxml"
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
    private void freelance(ActionEvent event) {
 
               // f.setTitle("Select Image File");
             
                
if (b==false)
{
   b=true;
      ab.setVisible(true);
   bf.setVisible(true);
       
      
   }else {
   
        b=false;
     ab.setVisible(false);
       bf.setVisible(false);
}



     
 
    
           
   }     

    @FXML
    private void sortir(ActionEvent event) {
        try {

Parent page1 =
FXMLLoader.load(getClass().getResource("/gui/session.fxml"));

Scene scene = new Scene(page1);

Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

stage.setScene(scene);

stage.show();

} catch (IOException ex) {

Logger.getLogger(utilisateurfx.class.getName()).log(Level.SEVERE,
null, ex);
}
    }

    void setDataFromPreviousScene(int dataToPass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        

    }

    

