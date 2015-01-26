/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decodeur.rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Théophile
 */
public class Destinataire {

    private BigInteger q; // premier et différent de p
    private BigInteger p;  // premier et différent de q
    private BigInteger n;  // n=q*p
    private BigInteger e;
    private BigInteger cle_dechiffrage;

    Destinataire(int size) {
        Random a=new Random();
        q=BigInteger.probablePrime(154,a);
        p=BigInteger.probablePrime(154,a);
        n=q.multiply(p);
        System.out.print(n);
    }

    public BigInteger get_n() {
        return n;
    }

    public BigInteger get_e() {
        return e;
    }

    private void init_e() {
        // e doit être premier avec (p-1)*(q-1) et strictement inférieur à (p-1)*(q-1)
    }

    private void calc_cle_dechiffrage() {
        // calcule de la clé de déchiffrage résoudre e*clé_dechiffrage%((p-1)*(q-1))=1
    }

    private int creer_nb_premier_Miller(int size) { // pas utiliser pour cause de performance
        if(size<=0 ||size >=1000){size=1;}
        boolean ispremier = false;
        int premier;
        Random a = new Random();
        premier = (int) ((int) Math.abs((a.nextInt()) % (Math.pow(10, size)-Math.pow(10, size-1)))+Math.pow(10, size-1));
        if (premier % 2 == 0) {
            premier++;
        }
        while (ispremier == false) {
            int s;
            float d;
            for (s = 0; Math.pow(2, s + 1) < premier - 1; s++);
            d = (premier - 1) / (float) Math.pow(2, s);
            boolean verif = false;
            for (int nbverif = 0; nbverif < (100/size)+1; nbverif++) {
                int temoin = Math.abs((a.nextInt() + 2) % (premier - 1));
                for (int r = 0; r < s - 1; r++) {
                    if (!(Math.pow(temoin, d) % premier != 1 && Math.pow(temoin, Math.pow(2, r) * d) != -1)) {
                        verif = true;
                    }
                }
            }
            //
            if (verif) {
                ispremier = true;
            } else {
                premier += 2;
            }
        }
        return premier;
    }
    
    private double creer_nb_premier_Solovay(int size) {  // pas utiliser pour cause de performance
        if(size<=0 ||size >=1000){size=1;}
        boolean ispremier = false;
        double premier;
        Random a = new Random();
        premier = (double) ((double) Math.abs((double) ((a.nextDouble()) % (Math.pow(10, size)-Math.pow(10, size-1))))+Math.pow(10, size-1));
        if (premier % 2 == 0) {
            premier++;
        }
        while (ispremier == false) {
            boolean verif = false;
            double v=(premier-1)/2;
            for (int nbverif = 0; nbverif < (100/size)*100+1; nbverif++) {
                double temoin = Math.abs((a.nextDouble()) % (premier - 1-2))+2;
                if(temoin/premier==Math.pow(temoin,v)%premier){
                    verif=false;
                }
            }
            //
            if (verif) {
                premier += 2;
            } else {
                ispremier = true;
            }
        }
        return premier;
    }
    

    public String decoder_message(String s) {
        return "";
    }
}
