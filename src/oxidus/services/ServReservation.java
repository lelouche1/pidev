/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import oxidus.entites.Modele;
import oxidus.entites.Reservation;
import oxidus.gui.Data;
import oxidus.utils.DBConnexion;

/**
 *
 * @author lelou
 */
public class ServReservation implements IntReservation {

    private Connection connection;

    public ServReservation() {
        this.connection = DBConnexion.getInstance().getConnexion();
    }

    @Override
    public int ajouterReservation(Reservation r) {
        int etat = -1;
        try {
            String req = "insert into reservations(id_voiture, id_user, nom_user, "
                    + "date_debut,date_fin, prix,modele,email_user,status) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            // preparedStatement.setint(1, null);
            preparedStatement.setInt(1, r.getId_voiture());
            preparedStatement.setInt(2, r.getId_user());
            preparedStatement.setString(3, r.getNom_user());
            java.sql.Date sqlDate = java.sql.Date.valueOf(r.getDate_debut());
            preparedStatement.setDate(4, sqlDate);
            java.sql.Date sqlDate2 = java.sql.Date.valueOf(r.getDate_fin());
            preparedStatement.setDate(5, sqlDate);
            
            int prix = prixModele(r.getModele());
            preparedStatement.setInt(6,prix);
            preparedStatement.setString(7, r.getModele());
            preparedStatement.setString(8, r.getEmail_user());
            if(r.getDate_fin().isBefore(LocalDate.now())){
                    r.setStatus(Data.ValeurStatus[2]);
                }else if(r.getDate_debut().isBefore(LocalDate.now()) && r.getDate_fin().isAfter(LocalDate.now())){
                    r.setStatus(Data.ValeurStatus[1]);
                }else if(r.getDate_debut().isAfter(LocalDate.now())){
                     r.setStatus(Data.ValeurStatus[0]);
                }
            preparedStatement.setString(9, r.getStatus());
            etat = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etat;
    }

    @Override
    public boolean supprimerReservation(Reservation r) {

        boolean status = false;
        try {
            String req = "delete from reservations where id_reservation=?";
            // String req = "delete from collaboration where getType_Reservation=? and titre=?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, r.getId_reservation());

            //  preparedStatement.setString(1, c.getType_Reservation());
            //  preparedStatement.setString(2, c.getTitre());
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean modifierReservation(Reservation r) {
        boolean status = false;
        try {
            String req = "update reservations set nom_user=?, date_debut=?, date_fin=?, modele=?, prix=? ,email_user where id_reservation=?";
            // String req = "update collaboration set titre=?, description=?, date_sortie=? where titre=? and description=?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, r.getNom_user());

            java.sql.Date sqlDate = java.sql.Date.valueOf(r.getDate_debut());
            preparedStatement.setDate(2, sqlDate);
            java.sql.Date sqlDate2 = java.sql.Date.valueOf(r.getDate_fin());
            preparedStatement.setDate(3, sqlDate2);
            preparedStatement.setString(4, r.getModele());
            //    preparedStatement.setInt(4, c.getStatus());
            for (Modele modele : Modele.values()) {
                if (modele.name().equals("NISSAN")) {
                    // Faire quelque chose si le modèle est NISSAN
                    preparedStatement.setInt(5, modele.NISSAN.getPrix());
                } else if (modele.name().equals("BMW")) {
                    // Faire quelque chose si le modèle est BMW
                    preparedStatement.setInt(5, modele.BMW.getPrix());
                } else if (modele.name().equals("PORSH")) {
                    // Faire quelque chose si le modèle est PORSH
                    preparedStatement.setInt(5, modele.PORSH.getPrix());
                }else if (modele.name().equals("TOYOTA")) {
                    // Faire quelque chose si le modèle est PORSH
                    preparedStatement.setInt(5, modele.TOYOTA.getPrix());
                }
                 preparedStatement.setString(6, r.getEmail_user());
            }
            preparedStatement.setInt(6, r.getId_reservation());
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<Reservation> afficherReservation() {
        List<Reservation> list = new ArrayList<Reservation>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from reservations");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId_reservation(rs.getInt("id_reservation"));
                r.setId_voiture(rs.getInt("id_voiture"));
                r.setId_user(rs.getInt("id_user"));
                r.setNom_user(rs.getString("nom_user"));
                java.sql.Date sqlDate = rs.getDate("date_debut");
                LocalDate dateDebut = sqlDate.toLocalDate();
                r.setDate_debut(dateDebut);
                java.sql.Date sqlDate2 = rs.getDate("date_fin");
                LocalDate datefin = sqlDate2.toLocalDate();
                r.setDate_debut(datefin);
                r.setModele(rs.getString("modele"));
                r.setPrix(rs.getInt("prix"));
                r.setEmail_user(rs.getString("email_user"));
                r.setStatus(rs.getString("status"));
                
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Reservation> afficherReservationUnClient(int id_reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> recherche(Reservation r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int prixModele(String marque){
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
    
    
        //----------------- statistique ----------------------
    public void createChart(AnchorPane anchorPane) {
        // Créer le graphique de type camembert
        PieChart chart = new PieChart();
        anchorPane.getChildren().add(chart);

        // Créer une tâche pour récupérer les données en arrière-plan
        Task<List<PieChart.Data>> task = new Task<List<PieChart.Data>>() {
            @Override
            protected List<PieChart.Data> call() throws Exception {
                List<PieChart.Data> dataList = new ArrayList<>();
                PreparedStatement stmt;
                // Récupérer les données depuis la base de données
                try {
                    String req = "SELECT modele, COUNT(*) FROM reservations GROUP BY modele";
                    stmt = connection.prepareStatement(req);
                    //    PreparedStatement stmt = conn.prepareStatement();
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String type = rs.getString("modele");
                        int count = rs.getInt(2);
                        dataList.add(new PieChart.Data(type, count));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return dataList;
            }
        };

        // Mettre à jour le graphique lorsque la tâche est terminée
        task.setOnSucceeded(event -> chart.getData().setAll(task.getValue()));

        // Lancer la tâche pour récupérer les données
        new Thread(task).start();
    }

    @Override
    public Reservation recherche(String nom, String modele) {

        //       List<Collaboration> listcol = new ArrayList<Collaboration>();
        String req = "SELECT * FROM reservations WHERE nom_user LIKE ? and modele LIKE ? ";

        Reservation rr = new Reservation();
        // Création d'un objet PreparedStatement
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(req);
            stmt.setString(1, "%" + nom + "%");
            stmt.setString(2, "%" + modele + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Récupération des données de chaque ligne
                rr.setId_reservation(rs.getInt("id_reservation"));
                rr.setId_user(rs.getInt("id_user"));
                rr.setId_voiture(rs.getInt("id_voiture"));
                rr.setNom_user(rs.getString("nom_user"));
                rr.setModele(rs.getString("modele"));
                java.sql.Date sqlDate1 = rs.getDate("date_debut");
                LocalDate dateDeb = sqlDate1.toLocalDate();
                rr.setDate_debut(dateDeb);
                java.sql.Date sqlDate2 = rs.getDate("date_fin");
                LocalDate dateFin = sqlDate2.toLocalDate();
                rr.setDate_fin(dateFin);
                rr.setPrix(rs.getInt("prix"));
                rr.setEmail_user(rs.getString("email_user"));
                rr.setStatus(rs.getString("status"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rr;
    }

    @Override
    public boolean modifierStatusReservation(Reservation r) {
 boolean status = false;
        try {
            String req = "update reservations set status=? where id_reservation=?";
            // String req = "update collaboration set titre=?, description=?, date_sortie=? where titre=? and description=?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, r.getStatus());           
            preparedStatement.setInt(2, r.getId_reservation());
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
