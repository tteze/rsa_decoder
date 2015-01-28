/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package decodeur.rsa;

import java.math.BigInteger;

/**
 *
 * @author Théophile
 */
public class Hacker {
    Hacker(){
    }
    public boolean intercept(BigInteger[] message,BigInteger n_dest,BigInteger e_dest,int size){
        try{ // on essaye de cracker
            crack(message,n_dest,e_dest,size);
        }
        catch(Exception e){ //on retourne l'échec du crack
            return false;
        };
        return true; // crack résussi
    }  

    private void crack(BigInteger[] message,BigInteger n_dest,BigInteger e_dest,int size){
        // algorithme de crack
    }
    
}