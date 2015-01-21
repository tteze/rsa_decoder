/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package decodeur.rsa;

import java.util.Scanner;

/**
 *
 * @author Théophile
 */
public class DecodeurRSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // initialisation
        Scanner s=new Scanner(System.in);
        String message="";
        String message_code="";
        String message_decode="";
        int size=5;                     // taille des nombres premier (nombre premier à 5 chiffre)
        Hacker hacker=new Hacker();
        Expediteur expediteur=new Expediteur();
        Destinataire destinataire=new Destinataire(size);
        
        // simulation
        
        message=s.nextLine(); // entrer le message
        
        message_code=expediteur.coder_message(message,destinataire.get_n(),destinataire.get_e()); //l'expediteur code un message
        
        if(hacker.intercept()==true){
                // message cracké
        }
        else{
                // message non cracké
        }
        
        message_decode=destinataire.decoder_message(message_code); // le destinataire lit le message
        
    }
}
