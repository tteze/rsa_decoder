/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package decodeur.rsa;

import java.util.Random;

/**
 *
 * @author Théophile
 */
public class Destinataire {
    private int q; // premier et différent de p
    private int p;  // premier et différent de q
    private int n;  // n=q*p
    private int e;
    private int cle_dechiffrage;
    
    
    Destinataire(int size){
        
    }
    
    public int get_n(){
        return n;
    }
    public int get_e(){
        return e;
    }
    private void init_e(){
        // e doit être premier avec (p-1)*(q-1) et strictement inférieur à (p-1)*(q-1)
    }
    
    private void calc_cle_dechiffrage(){
            // calcule de la clé de déchiffrage résoudre e*clé_dechiffrage%((p-1)*(q-1))=1
    }
    
    private int creer_nb_premier(int size){
        boolean ispremier=false;
        int premier;
        Random a=new Random();
        premier=(int) (((a.nextInt()+Math.pow(10, size)))%Math.pow(10, size+1));
        while(ispremier==false){
            
            if(true){
                ispremier=true;
            }
            else{
                premier=(int) (((a.nextInt()+Math.pow(10, size)))%Math.pow(10, size+1));
            }
        }
        return premier;
    }
    
    public String decoder_message(String s){
        return "";
    }
}
