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
public class Expediteur {
    
    Expediteur(){
    }
    
    public BigInteger[] coder_message(String s,BigInteger n_dest,BigInteger e_dest){
        BigInteger[] a=new BigInteger[s.length()];
        char[] m=s.toCharArray();
        for(int i=0;i<s.length();i++){
            a[i]=BigInteger.valueOf((int)m[i]).modPow(e_dest, n_dest);
        }
        return a;
    }
}

