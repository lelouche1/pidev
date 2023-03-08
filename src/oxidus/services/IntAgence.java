/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxidus.services;

import java.util.List;
import oxidus.entites.Agence;

/**
 *
 * @author lelou
 */
public interface IntAgence {
    
     int ajouterAgence(Agence a);
    boolean supprimerAgence(Agence a);
    boolean modifierAgence(Agence a);
    List<Agence> afficherAgence();
    List<Agence> afficherUneAgence(int id_Agence); 
    List<Agence> recherche(Agence a);
    public Agence recherche(String nom, String adresse);
}
