/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package decodeur.rsa;

import java.math.BigInteger;
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
        BigInteger[] message_code;
        String message_decode="";
        int size=20;                     // taille des nombres premier (nombre premier à 5 chiffre)
        Hacker hacker=new Hacker();
        Expediteur expediteur=new Expediteur();
        Destinataire destinataire=new Destinataire(size);
        
        // simulation
        
        message=s.nextLine(); // entrer le message
        
        //l'expediteur code un message
        message_code=expediteur.coder_message(message,destinataire.get_n(),destinataire.get_e()); 
        
        // affichage du message codé
        System.out.print("message codé par l'expediteur:");     
        for(int i=0;i<message.length();i++){
            System.out.print(" "+message_code[i]);
        }
        System.out.println();
        System.out.println();
        System.out.println("---Try to crack---");
        // essai de crack
        if(hacker.intercept(message_code,destinataire.get_n(),destinataire.get_e(),message.length())==false){
                System.out.println("message non cracké");
        }
        System.out.println();
        // le message arive à bonne destination et se fait décodé
        message_decode=destinataire.decoder_message(message_code,message.length()); // le destinataire lit le message
        
        // affichage du message décodé
        System.out.println("message décodé par le destinataire: "+message_decode);
    }
}
