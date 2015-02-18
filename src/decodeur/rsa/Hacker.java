/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decodeur.rsa;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Théophile
 */
public class Hacker {

    Hacker() {
    }

    public boolean intercept(BigInteger[] message, BigInteger n_dest, BigInteger e_dest, int size) {
        try { // on essaye de cracker
            crack_recherche_cle_privee(message,n_dest,e_dest, size);
        } catch (Exception e) { //on retourne l'échec du crack
            return false;
        };
        return true; // crack résussi
    }

    private void crack_frequentiel(BigInteger[] message, int size) {
        // algorithme de crack par fréquences
        //on trie les lettres par oredres d'occurences
        int taille = 0;
        boolean ajout;
        BigInteger[] trie = new BigInteger[size];
        int[] occurence = new int[size];

        for (int i = 0; i < size; i++) {
            occurence[i] = 0;
        }
        for (int i = 0; i < size; i++) {        //on compte les occurences
            ajout = true;
            for (int a = 0; a < taille; a++) {
                if (message[i].equals(trie[a])) {
                    occurence[a]++;
                    ajout = false;
                }
            }
            if (ajout) {
                trie[taille] = message[i];
                taille++;
            }
        }
        for (int i = 0; i < taille; i++) {      // on trie par ordre d'occurences
            int temp = occurence[i];
            BigInteger temp2 = trie[i];
            int j = i;
            while (j > 0 && occurence[j - 1] < temp) {
                occurence[j] = occurence[j - 1];
                trie[j] = trie[j - 1];
                j = j - 1;
            }
            occurence[j] = temp;
            trie[j] = temp2;
        }
        String s;                           // on compare avec l'alphabet
        char[] c = new char[size];
        char[] francais = {' ', 'e', 's', 'a', 'i', 'n', 't', 'r', 'u', 'l', 'o', 'd', 'c', 'p', 'm', 'v', 'q', 'g', 'f', 'h', 'b', 'x', 'j', 'y', 'z', 'k', 'w'};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < taille; j++) {
                if (message[i].equals(trie[j])) {
                    c[i] = francais[j];
                }
            }
        }
        s = String.copyValueOf(c);
        System.out.println("message cracké par fréquence: " + s);

        boolean acceptation = false;
        Scanner scan = new Scanner(System.in);
        String demande;
        int count = 0;
        Random r;
        Scanner read = new Scanner(s);
        while (acceptation == false) {
            String mot = read.next();
            System.out.print("Est ce que vous connaissez " + mot + " ? (oui/non/corrige): ");
            demande = scan.nextLine();
            switch (demande) {
                case "oui":
                    count++;
                    break;
                case "non":
                    count = 0;
                    break;
                case "corrige":
                    System.out.print("entrez le mot corrigé: ");
                    String mot_cor = scan.nextLine();
                    for(int u=0;u<mot.length();u++){
                        if(mot.charAt(u)!=mot_cor.charAt(u)){
                            for(int e=0;e<francais.length;e++){
                                if(mot.charAt(u)==francais[e]){
                                    francais[e]=mot_cor.charAt(u);
                                }
                                else{
                                    if(mot_cor.charAt(u)==francais[e]){
                                        francais[e]=mot.charAt(u);
                                    }
                                }
                            }
                            char car=mot.charAt(u);
                            mot.replace(car,' ');
                            mot.replace(mot_cor.charAt(u), car);
                            mot.replace(' ', mot_cor.charAt(u));
                        }
                    }
                    c = new char[size];
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < taille; j++) {
                            if (message[i].equals(trie[j])) {
                                c[i] = francais[j];
                            }
                        }
                    }
                    s = String.copyValueOf(c);
                    count=0;
                    
                    read=new Scanner(s);
                    System.out.println("message cracké par fréquence: " + s);
                    break;
            }
            if (count >= 10) {
                acceptation = true;
            }
        }
        System.out.println("message cracké par fréquence: "+s);

    }

    private void crack_recherche_cle_privee(BigInteger[] message, BigInteger n_dest, BigInteger e_dest, int size){
       MPQS facto=new MPQS(n_dest);
       BigInteger p=facto.get_P();
       BigInteger q=facto.get_Q();
       BigInteger cle_dechiffrage=e_dest.modInverse(p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1))));
        String s;
        char[] c=new char[size];
        for(int i=0;i<size;i++){
            c[i]=(char)message[i].modPow(cle_dechiffrage,n_dest).intValue();
        }
        s=String.valueOf(c);
        System.out.println("Message cracké: "+s);
    }
}
