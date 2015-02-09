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
            String freq;
            freq=crack(message,size);
            System.out.println("Message cracké par fréquence: "+freq);
        }
        catch(Exception e){ //on retourne l'échec du crack
            return false;
        };
        return true; // crack résussi
    }  

    private String crack(BigInteger[] message,int size){
        // algorithme de crack par fréquences
        //on trie les lettres par oredres d'occurences
        int taille=0;
        boolean ajout;
        BigInteger[]trie= new BigInteger[size];
        int[] occurence=new int[size];
        
        for(int i=0;i<size;i++){
            occurence[i]=0;
        }
        for(int i=0;i<size;i++){        //on compte les occurences
            ajout=true;
            for(int a=0;a<taille;a++){
                if(message[i].equals(trie[a])){
                    occurence[a]++;
                    ajout=false;
                }
            }
            if(ajout){
                trie[taille]=message[i];
                taille++;
            }
        }
        for(int i=0;i<taille;i++){      // on trie par ordre d'occurences
            int temp=occurence[i];
            BigInteger temp2=trie[i];
            int j=i;
            while(j>0 && occurence[j-1]<temp){
                occurence[j]=occurence[j-1];
                trie[j]=trie[j-1];
                j=j-1;
            }
            occurence[j]=temp;
            trie[j]=temp2;
        }
        String s;                           // on compare avec l'alphabet
        char[] c=new char[size];
        char[] francais={' ','e','s','a','i','n','t','r','u','l','o','d','c','p','m','v','q','g','f','h','b','x','j','y','z','k','w'};
        for(int i=0;i<size;i++){
            for(int j=0;j<taille;j++){
                if(message[i].equals(trie[j])){
                    c[i]=francais[j];
                }
            }
        }
        s=String.copyValueOf(c);
        return s;
    }
    
}