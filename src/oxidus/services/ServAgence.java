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
import oxidus.entites.Agence;
import oxidus.entites.Reservation;
import oxidus.utils.DBConnexion;

/**
 *
 * @author lelou
 */
public class ServAgence implements IntAgence {

    private Connection connection;
    
     public ServAgence() {
        this.connection = DBConnexion.getInstance().getConnexion();
    }

    @Override
    public int ajouterAgence(Agence a) {

        int etat = -1;
        try {
            String req = "insert into agence(nomAgence, nbreVehicule, "
                    + "emailAg,ville,adresse) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
          //   preparedStatement.setString(1, null);
            preparedStatement.setString(1, a.getNomAg());
            preparedStatement.setInt(2, a.getNbreVehicule());
            preparedStatement.setString(3, a.getEmailAg());
            preparedStatement.setString(4, a.getVilleAgence());
            preparedStatement.setString(5, a.getAdresse());
            
            etat = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etat;
    }

    @Override
    public boolean supprimerAgence(Agence a) {
        boolean status = false;
        try {
            String req = "delete from agence where id_agence = ?";
            // String req = "delete from collaboration where getType_Reservation=? and titre=?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, a.getId_agence());

            //  preparedStatement.setString(1, c.getType_Reservation());
            //  preparedStatement.setString(2, c.getTitre());
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean modifierAgence(Agence a) {

        boolean status = false;
        try {
            String req = "update agence set nomAgence=?, nbreVehicule=?, emailAg=?, "
                    + "ville=?, adresse=? where id_agence=?";
            // String req = "update collaboration set titre=?, description=?, date_sortie=? where titre=? and description=?";
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setString(1, a.getNomAg());

            preparedStatement.setInt(2, a.getNbreVehicule());
            preparedStatement.setString(3, a.getEmailAg());
            preparedStatement.setString(4, a.getVilleAgence());
            preparedStatement.setString(5, a.getAdresse());
             preparedStatement.setInt(6, a.getId_agence());
            
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public List<Agence> afficherAgence() {
        List<Agence> list = new ArrayList<Agence>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from agence");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Agence a = new Agence();
                a.setId_agence(rs.getInt("id_agence"));
                a.setAdresse(rs.getString("adresse"));
                a.setEmailAg(rs.getString("emailAg"));
                a.setNbreVehicule(rs.getInt("nbreVehicule"));
                a.setVilleAgence(rs.getString("ville"));
                a.setNomAg(rs.getString("nomAgence"));
                list.add(a);
                //       System.out.println("service collaboration");
                //       System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Agence> afficherUneAgence(int id_Agence) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Agence> recherche(Agence a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Agence recherche(String nom, String adresse) {
 //       List<Collaboration> listcol = new ArrayList<Collaboration>();
        String req = "SELECT * FROM agence WHERE nomAgence LIKE ? and adresse LIKE ? ";

        Agence aa = new Agence();
        // Création d'un objet PreparedStatement
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(req);
            stmt.setString(1, "%" + nom + "%");
            stmt.setString(2, "%" + adresse + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Récupération des données de chaque ligne
                aa.setId_agence(rs.getInt("id_agence"));
                aa.setAdresse(rs.getString("adresse"));
              aa.setNomAg(rs.getString("nomAgence"));
                aa.setVilleAgence(rs.getString("ville"));
                aa.setEmailAg(rs.getString("emailAg"));
                aa.setNbreVehicule(rs.getInt("nbreVehicule"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aa;
    }

}
