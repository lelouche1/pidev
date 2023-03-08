/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.entites;

/**
 *
 * @author lelou
 */
public class Agence {
    private int id_agence;
    private String nomAg;
    private int nbreVehicule;
    private String emailAg;
    private String villeAgence;
    private String adresse;

    public Agence() {
    }

    public Agence(int id_agence, String nomAg, int nbreVehicule, String emailAg, String villeAgence, String adresse) {
        this.id_agence = id_agence;
        this.nomAg = nomAg;
        this.nbreVehicule = nbreVehicule;
        this.emailAg = emailAg;
        this.villeAgence = villeAgence;
        this.adresse = adresse;
    }

    public int getId_agence() {
        return id_agence;
    }

    public String getNomAg() {
        return nomAg;
    }

    public int getNbreVehicule() {
        return nbreVehicule;
    }

    public String getEmailAg() {
        return emailAg;
    }

    public String getVilleAgence() {
        return villeAgence;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setId_agence(int id_agence) {
        this.id_agence = id_agence;
    }

    public void setNomAg(String nomAg) {
        this.nomAg = nomAg;
    }

    public void setNbreVehicule(int nbreVehicule) {
        this.nbreVehicule = nbreVehicule;
    }

    public void setEmailAg(String emailAg) {
        this.emailAg = emailAg;
    }

    public void setVilleAgence(String villeAgence) {
        this.villeAgence = villeAgence;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Agence{" + "id_agence=" + id_agence + ", nomAg=" + nomAg + ", nbreVehicule=" + nbreVehicule + ", emailAg=" + emailAg + ", villeAgence=" + villeAgence + ", adresse=" + adresse + '}';
    }
    
    
    
}
