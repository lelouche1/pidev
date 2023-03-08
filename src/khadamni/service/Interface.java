/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khadamni.service;

import entity.Admin;
import entity.Utilisateur;
import java.util.List;

/**
 *
 * @author lordg
 */
public interface Interface < T >{
    public void ajouter(T t);
    public List<T> afficher();
    public   T findById(int id);
     public void modifier(String nom,int id);
      public void supprimer(int id);
}
