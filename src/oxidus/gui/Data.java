/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.time.LocalDate;
import javafx.scene.control.Alert;
import oxidus.entites.Modele;

/**
 *
 * @author lelou
 */
public class Data {
    
    public static String nom_reser;
    public static LocalDate dateDebut;
    public static LocalDate dateFin;
    public static String modele;
     public static long prix_total;
     public static int prix;
     public static long nbre_jours;
     public static int id;
   
     public static int prixModele(String marque){
         for (Modele modele : Modele.values()) {      
                  
                if (modele.name().equals(marque)) {
                    // Faire quelque chose si le modèle est BMW
                    return modele.BMW.getPrix();
                } else if (modele.name().equals(marque)) {
                    // Faire quelque chose si le modèle est PORSH
                    return  modele.PORSH.getPrix();
                }else if (modele.name().equals(marque)) {
                    // Faire quelque chose si le modèle est PORSH
                    return modele.TOYOTA.getPrix();
                }
        }
         return -1;
 
     }
     
      public static void warning(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        //alert.setContentText(message);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void information(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        //   alert.setContentText(message);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
