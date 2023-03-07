/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.entites;

import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author lelou
 */
public class Reservation {
    
    
private int id_reservation;

private int id_voiture=3;

private int id_user=4;

private String nom_user="yassin";

private LocalDate date_debut = LocalDate.now();

private LocalDate date_fin = LocalDate.now();

private int prix=5700;

private Modele enumModele;

private String modele="yassin";

    public Reservation() {
    }

   
    public int getId_reservation() {
        return id_reservation;
    }

    public int getId_voiture() {
        return id_voiture;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public int getPrix() {
        return prix;
    }


    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setId_voiture(int id_voiture) {
        this.id_voiture = id_voiture;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Modele getEnumModele() {
        return enumModele;
    }

    public String getModele() {
        return modele;
    }

    public void setEnumModele(Modele enumModele) {
        this.enumModele = enumModele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

   

}
