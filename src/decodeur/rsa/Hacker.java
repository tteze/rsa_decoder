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
    public boolean intercept(){
        try{
            crack();
        }
        catch(Exception e){
            return false;
        };
        return true;
    }  

    private void crack(){
        
    }
    
}