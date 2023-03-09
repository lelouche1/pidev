/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;  


/**
 *
 * @author lelou
 */


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
                              
       //  Parent root = FXMLLoader.load(getClass().getResource("ConsulterAgence.fxml")); 
       //  Parent root = FXMLLoader.load(getClass().getResource("AdminMenuAgence.fxml"));
      //  Parent root = FXMLLoader.load(getClass().getResource("ajouterAgence.fxml"));      
         Parent root = FXMLLoader.load(getClass().getResource("AjourReservation.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("menuReservation.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("AdminGestionReservation.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    } 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}   


    
