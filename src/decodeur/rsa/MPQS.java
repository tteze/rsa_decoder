package decodeur.rsa;

import java.math.BigInteger;
import java.util.BitSet;
class  MPQS{
	  /*
    Racine carrée entière.
    Renvoie le nombre maximal dont le carré est inférieur ou égal à n
    [Méthode dite de Newton]

  
*/

  
  static BigInteger sqrt(BigInteger n) {
    int nb = n.bitLength() ;
    BigInteger a ;

    a = BigInteger.valueOf(1);
    a = a.shiftLeft((nb+1)/2) ;

    for (int i=0;i<=1001;i++){
      a = ((n.divide(a)).add(a)).shiftRight(1);
    } 
    return a ;
  }
  
  /*
    Calcul du symbole de legendre, noté bizarrement (n/p)
    Pour p premier on a :
      - (n/p) = 0, ssi p divise n.
      Et sinon, 
      - (n/p) = 1  ,ssi n est un résidu quadratique modulo p.
      - (n/p) = -1 ,ssi n n'est pas un résidu quadratique modulo p.

  
*/

  
  private static int legendre(BigInteger n, int p) {
    int a = n.remainder(BigInteger.valueOf(p)).intValue() ;
    int m = p ;
    int t = 1 ;

    while (a != 0) {
      int k = m % 8 ;
      boolean zyva = k == 3 || k == 5 ;

      while (a % 2 == 0) {
        a /= 2 ;
        if (zyva)
          t = -t ;
      }
      int swap = a ; a = m ; m = swap ;
      int x = a % 4, y = m % 4 ;
      if (x == 3 && x == y)
        t = -t ;
      a = a % m ;
    }
    if (m == 1)
      return t ;
    else
      return 0 ;
  }
  
  private static int multMod(int x, int y, int m) {
    long w = (long)x * (long)y ;
    return (int)(w % m) ;
  }

  static int powMod(int x, int k, int m) {
    if (k == 0)
      return 1 ;
    else {
      int r = powMod(x, k/2, m) ;
      r = multMod(r, r, m) ;
      if (k % 2 == 0)
        return r ;
      else
        return multMod(x, r, m) ;
    }
  }

  /*
    Calculer une solution de l'équation x^2 = n (mod p).

    Important:
    On suppose que cette solution existe, c'est dire que
    n est un résidu quadratique modulo p.
    [Algorithme de Shanks-Tonelli.]

  
*/


  private static int sqrtMod(BigInteger n, int p) {
    int a = n.remainder(BigInteger.valueOf(p)).intValue() ;
    if (p % 4 == 3) {
      return powMod(a, (p+1)/4, p) ;
    } else {
      int d ;
      do {
        d = 2+Integer.parseInt(""+Math.round(Math.random()*(p-2))) ;
      } while (legendre(BigInteger.valueOf(d), p)==1) ;
      int s = 0 ;
      int t = p-1 ;
      while (t % 2 == 0) {
        s++ ; t >>=1;
      }
      int A = powMod(a, t, p) ;
      int D = powMod(d, t, p) ;
      int m = 0 ;
      int ADpowM = A ;
      int twoPowI = 1 ;
      int D2 = D ;
      for (int exp = 1 << s-1 ; exp !=0 ;
           exp >>>= 1, twoPowI <<= 1, D2 = multMod(D2, D2, p)) {
        if (powMod(ADpowM, exp, p) == p-1) {
          m += twoPowI ;
          ADpowM = multMod(ADpowM, D2, p) ;
        }
      }
      return multMod(powMod(a, (t+1)/2, p), powMod(D, m/2, p), p) ;
    }
  }
  
  static int legendre(BigInteger a,BigInteger p){
    BigInteger ret=a.modPow(p.shiftRight(1),p);
    if (ret.equals(p.subtract(BigI(1))))return -1;
    //System.out.println(ret);
    return ret.intValue();
  }
  
  private static BigInteger sqrtMod(BigInteger n, BigInteger p) {
    BigInteger a = n.remainder(p);
    if (p.remainder(BigI(4)).equals(BigI(3))) {
      return a.modPow((p.add(BigI(1))).shiftRight(2), p) ;
    } else {
      int d ;
      do {
        d = 2+Integer.parseInt(""+Math.round(Math.random()*1000)) ;
      } while (legendre(BigI(d),p)==1) ;
      int s = 0 ;
      BigInteger t = p.subtract(BigI(1));
      while (t.mod(BigI(2)).equals(BigI(0))) {
        s++ ; t=t.shiftRight(1);
      }
      BigInteger A =a.modPow(t,p) ;
      BigInteger D =BigI(d).modPow(t,p) ;
      int m = 0 ;
      BigInteger ADpowM = A ;
      int twoPowI = 1 ;
      BigInteger D2 = D ;
      for (int exp = 1 << s-1 ; exp !=0 ;
           exp >>>= 1, twoPowI <<= 1, D2 =D2.multiply(D2).remainder(p)) {
        if (ADpowM.modPow(BigI(exp),p).equals(p.subtract(BigI(1)))) {
          m += twoPowI ;
          ADpowM =ADpowM.multiply(D2).remainder(p);
        }
      }
      return (a.modPow(t.add(BigI(1)).shiftRight(1), p).multiply(D.modPow(BigI(m>>1), p))).remainder(p) ;
    }
  }
	
	static byte log2(int n){          // renvoie le log en base 2
		int k=n;
		byte log=1;
		while (k!=1) {
			k>>=1;  // k=k/2
			log++;
		}
		return log;
	}
	static boolean prima(int i){
		if (i==2)return true;
		if (i==3)return true;
		if (i==5)return true;
		final int rac=(int)Math.sqrt(i)+1;
		int k;
		for (k=2;k!=rac;k++)
			if (i%k==0)break;	
		if (k==rac)return true;
		return false;
	}
	
	static BigInteger BigI(int i){
		return(BigInteger.valueOf(i));
	}
	
	static BigInteger f(long i,BigInteger racine,BigInteger n){  
	// calcule (racine+i)^2-n
		BigInteger k=racine.add(BigInteger.valueOf(i));
		return k.multiply(k).subtract(n);
	}
	
	static BigInteger Pow(int x,int k){ //renvoie x^k
		if (k == 0)
			return BigI(1);
		else {
			BigInteger r=Pow(x, k/2) ;
			r=r.multiply(r);
			if (k % 2 == 0)
				return r ;
			else
				return r.multiply(BigI(x)) ;
		}
	}
	
	public static BigInteger nextprime(BigInteger a){
		final BigInteger deux=BigI(2);
		if (a.equals(deux)) return BigI(3);
		BigInteger essai=a.add(deux);
		if (a.remainder(deux).equals(BigI(0))) essai=essai.subtract(BigI(1)); 
		
		do{essai=essai.add(deux);}while (!essai.isProbablePrime(80));
		return essai;
	}
	
	public MPQS(BigInteger[] message, BigInteger n_dest, BigInteger e_dest, int size){
		long deb=System.currentTimeMillis();
		final int taille=300000;
		//final int base=41903;
		final double ln2=Math.log(2);
		final int sup=30;
		final int min=10;
		final int tailleM1=taille-1;
		final BigInteger un=BigI(1);
		final BigInteger zero=BigI(0);
		final int multiplier=1;
		int tailprem=1,j,j2,k,nblisse=-1,nb=0,nbfaux=0,premi,racPrem,tmp;
		BigInteger n=n_dest,
		// entier à factoriser
			inter,ent,racine,fact,premj,carre,a,q,b,facto,ainv,b0,b1,h;
		n=n.multiply(BigI(multiplier));
		final double lnN=Math.log(n.doubleValue());
		final double temp=4*Math.exp(Math.sqrt(lnN*Math.log(lnN)/12));
		final int base=(int)(temp*Math.log(temp));
		System.out.println(base);
		byte expo,ajout,borne;
		
		q=sqrt(sqrt(BigI(2).multiply(n)).divide(BigI(taille)));
		racine=sqrt(n).add(un);
		System.out.println(n.divide(BigI(multiplier)));
		for (int i=2;i<=base;i++)
			if (prima(i)&&legendre(n,i)==1)tailprem++;
		int[] prem=new int [tailprem+1];
		tailprem=1;
		prem[0]=-1;
		for (int i=2;i<=base;i++)
			if (prima(i)&&legendre(n,i)==1) {
				prem[tailprem]=i;
				tailprem++;
			}
		final int li=tailprem+sup,col=tailprem;
		final int liM1=li-1;
		BigInteger[] tab =new BigInteger[li];
		BigInteger[] tabQ=new BigInteger[li];
		byte[][] matrexp=new byte [li][col];
		BitSet[]matr=new BitSet[li];
		{
		int[] tsqrt=new int [col];
		byte[] log2=new byte[col];
		byte[] w=new  byte [taille]; 
		byte[] zeros=new byte [taille];		
		for (int i=0;i<taille;i++)zeros[i]=0; 
		for (int i=0;i<li;i++)matr[i]=new BitSet(col); 
		for (int i=min;i!=tailprem;i++){
			premi=prem[i];
			tsqrt[i]=sqrtMod(n,premi);
			log2[i]=(byte)Math.ceil(Math.log(premi)/ln2);
		}
		System.out.println(tailprem+" "+base);
		while (nblisse<li){   // tant que l'on a pas assez de résidus
			nb++;
			q=nextprime(q);
			while (legendre(n,q)!=1)q=nextprime(q);
			a=q.pow(2);
			b0=sqrtMod(n,q);
			h=(n.subtract(b0.pow(2))).divide(q);
			b1=(h.multiply((BigI(2).multiply(b0)).modInverse(q))).mod(q);
			b=(b0.add(b1.multiply(q))).remainder(a);
			if ((b.remainder(BigI(2))).equals(BigI(0)))b=a.subtract(b);
			borne=(byte)(racine.multiply(BigI(taille)).bitLength()-log2(base));
			System.arraycopy(zeros,0,w,0,taille);
			for(k=min;k!=tailprem-1;k++){
				premi=prem[k];
				ajout=log2[k];
				ainv=a.modInverse(BigI(premi));
				j =((ainv.multiply(BigI( tsqrt[k]).subtract(b))).mod(BigI(premi))).intValue();
				j2=((ainv.multiply(BigI(-tsqrt[k]).subtract(b))).mod(BigI(premi))).intValue();
				if (j<j2){
					tmp=j2;
					j2=j;
					j=tmp;
				}
				j2=j-j2;
				while (j<taille){
					w[j]+=ajout;
					w[j-j2]+=ajout;
					j+=premi;
				}
			}
			for (int i=0;i<taille;i++){
				for(;w[i]<borne;i++)if (i==tailleM1)break;
				if (i==taille)break;
				if (w[i]>=borne) { 
	// si le nb à l'air lisse(tout ses facteurs sont plus petits que b
					nblisse++;
					facto=(a.multiply(BigI(i))).add(b);
					ent=((facto.pow(2)).subtract(n)).divide(a);
					// on le factorise 
					matr[nblisse].clear();
					if (ent.compareTo(zero)<0){
						ent=ent.negate();
						matrexp[nblisse][0]=1;
						matr[nblisse].set(0);
					}else{
						matrexp[nblisse][0]=0;
					}
					tab [nblisse]=facto;
					tabQ[nblisse]=q;
					j=1;expo=0;premj=BigI(2);
					while(true){
						if (ent.remainder(premj).equals(zero)){
							ent=ent.divide(premj);
							expo++;
							if (ent.equals(un)){
								matrexp[nblisse][j]=(byte)expo;
								// Il faut écrire le dernier exposant
								if ((expo&1)==1)matr[nblisse].set(j);
								System.arraycopy(zeros,0,matrexp[nblisse],j+1,tailprem-j-1);
								for (j++;j<col;j++)matr[nblisse].clear(j);
								break;  // La factorisation est complète
							}
						}
						else{
							matrexp[nblisse][j]=(byte)expo;
							if ((expo&1)==1)matr[nblisse].set(j);
							expo=0;
							j++;
							if (j==tailprem){
								nblisse--;
								nbfaux++;
								break;
// Les nombres premiers de la base ont été parcouru, le nombre n'est pas lisse
							}
							premj=BigInteger.valueOf(prem[j]);
						}
					}
					if (nblisse==(li-1)){      
			// On a trouvé toutes les relations suffisantes
						nblisse++;
						break;
					}
				}
			}
			if (nb%20==0)
				System.out.println("Fini : "+nblisse+"("+nbfaux+")="+(nblisse*100)/(tailprem+sup)+"%	");
		} }
		System.out.println("");
		System.out.println("J'ai fini le crible avec "+nbfaux+" qui sont faux");
		System.out.println("Criblage fini en "+(System.currentTimeMillis()-deb)+" ms");
		System.out.println("Un nombre lisse toutes les "+((long)nb*(long)taille)/li+" valeurs");
		System.out.println("Crible sur "+nb*2+" polynomes");
		
		BitSet[]T=new BitSet[li];
		BitSet bonPiv=new BitSet(li);
		j=0;
		for (int i=0;i<li;i++){
			T[i]=new BitSet(li);
			bonPiv.set(i);
			for (int l=0;l<li;l++)
				T[i].clear(l);
		}
		for (int i=0;i<li;i++) T[i].set(i);			// T devient une matrice identité
		int piv,rech;
		for (int m=0;m<col;m++){ // on fait le pivot de Gauss
			piv=0;
			while(true){
				try{
				  while (!matr[piv].get(m)) piv++;
				}
				catch(Exception e){piv=-1;}; 
				if (piv==-1) break;
				if (bonPiv.get(piv))break;
				piv++;
			};
			if (piv==-1)continue;
			bonPiv.clear(piv);
			j2=-1;
			while (true){
				if (j2==liM1)break;
				j2++;
				try{
				  while (!matr[j2].get(m))j2++;
				}
				catch(Exception e){j2=-1;}// On a dépassé les bornes du tableau 
				if (j2==-1)break;
				if (j2!=piv){
					matr[j2].xor(matr[piv]);
					T[j2].xor(T[piv]);
				}
			}
		}
		for(int i=0;i!=li;i++)matr[i]=new BitSet(0);
		for(int i=0;i!=col;i++)T  [i]=new BitSet(0);
		System.out.println("Pivot de Gauss termine");
		int []ligneExp=new int [col];
		for (int i=col;i<li;i++){
			BigInteger prodQ=un;
			ent=BigI(1);inter=BigI(1);
			for (int l=0;l<col;l++)ligneExp[l]=0;
			for (int l=0;l<li;l++)
				if (T[i].get(l)){
					ent=(ent.multiply(tab[l])).mod(n);
					for (int m=0;m<col;m++)ligneExp[m]+=matrexp[l][m];
					prodQ=(prodQ.multiply(tabQ[l])).remainder(n);
				}
			for (int l=0;l<col;l++)
				inter=(inter.multiply(Pow(prem[l],ligneExp[l]/2))).remainder(n);
			fact=n.gcd(ent.subtract(inter.multiply(prodQ)));
			if((!fact.equals(un))&&(!fact.equals(n))){
				if (fact.equals(BigI(multiplier)))continue;
				if ((n.divide(fact)).equals(BigI(multiplier)))continue;
				if (fact.mod(BigI(multiplier)).equals(zero))
				System.out.println((fact.divide(BigI(multiplier)))+"*"+n.divide(fact));
				else System.out.println((fact)+"*"+((n.divide(fact)).divide(BigI(multiplier))));
				break;
			}
		}
		System.out.println((System.currentTimeMillis()-deb)+" millisecondes");
	}
}