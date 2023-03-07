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
public enum Modele {
    TOYOTA(200), NISSAN(180),BMW(300),PORSH(500);
    private int prix;

    Modele(int prix) {
        this.prix=prix;
    }
    
    public int getPrix(){
        return prix;
    }
    
}
