/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.services;

import java.util.List;
import oxidus.entites.Reservation;

/**
 *
 * @author lelou
 */
public interface IntReservation {
     int ajouterReservation(Reservation r);
    boolean supprimerReservation(Reservation r);
    boolean modifierReservation(Reservation r);
    List<Reservation> afficherReservation();
    List<Reservation> afficherReservationUnClient(int id_reservation); 
    List<Reservation> recherche(Reservation r);
    public Reservation recherche(String nom, String modele);
}
