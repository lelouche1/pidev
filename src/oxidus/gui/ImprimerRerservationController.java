/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lelou
 */
public class ImprimerRerservationController implements Initializable {

    
    @FXML
    private Button imprimer_reservation;

    @FXML
    private Label Label_nom;
    
    @FXML
    private Label label_prix_jour;

    @FXML
    private Label Label_modele;

    @FXML
    private Label Label_dateDeb;

    @FXML
    private Label Label_dateFin;

    @FXML
    private Label Label_prixTotal;

    @FXML
    private Button annuler_impression;
    
    @FXML
    private Label LabelJour;
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    @FXML
    void annuler_impression(ActionEvent event) {
        Data.warning("annuler", "impression annuler");
       try {
                    // Charger la scène2.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menuReservation.fxml"));
                    Parent root = loader.load();


                    // Afficher la scène2
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) annuler_impression.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
    }

    @FXML
    void imprimer_reservation(ActionEvent event) {
         // Obtention de la fenêtre principale
        Stage primaryStage = (Stage) imprimer_reservation.getScene().getWindow();

        // Obtention d'un objet PrinterJob pour gérer l'impression
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null) {
            // Obtention de l'interface graphique à imprimer
            Scene scene = primaryStage.getScene();

            // Configuration des paramètres d'impression
            Printer printer = printerJob.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            double scaleX = pageLayout.getPrintableWidth() / scene.getWidth();
            double scaleY = pageLayout.getPrintableHeight() / scene.getHeight();
            scene.getRoot().setScaleX(scaleX);
            scene.getRoot().setScaleY(scaleY);

            // Impression de l'interface graphique
            boolean success = printerJob.printPage(pageLayout, scene.getRoot());
            if (success) {
                printerJob.endJob();
            }
        }
        try {
                    // Charger la scène2.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menuReservation.fxml"));
                    Parent root = loader.load();


                    // Afficher la scène2
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) imprimer_reservation.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
    }

  public void afficherModif(String nom,String modele ,LocalDate dateDeb, LocalDate dateFin, Integer prixx, Long prix_total, Long nbJour) {
        Label_nom.setText(nom);
        Label_modele.setText(modele);
        Label_dateDeb.setText(dateDeb.toString());
        Label_dateFin.setText(dateFin.toString());
        label_prix_jour.setText(prixx.toString());
        Label_prixTotal.setText(prix_total.toString());
        LabelJour.setText(nbJour.toString());
  }
    
}
