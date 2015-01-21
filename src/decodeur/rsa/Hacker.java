/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package decodeur.rsa;

/**
 *
 * @author Théophile
 */
public class Hacker {
    Hacker(){
    }
    public boolean intercept(String message,int n_dest,int e_dest){
        try{
            crack(message,n_dest,e_dest);
        }
        catch(Exception e){
            return false;
        };
        return true;
    }  

    private void crack(String message,int n_dest,int e_dest){
        // algorithme de crack
    }
    
}