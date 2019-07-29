import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Encryption {

	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = new BigInteger("2");
	private static final BigInteger THREE = new BigInteger("3");
	private static BigInteger p;
	private static BigInteger q;
	private static BigInteger n;
	private static BigInteger totient;
	private static BigInteger publicKey;
	private static BigInteger privateKey;
	private static BigInteger totalOfPlain;
	private static BigInteger totalOfCipher;
	
	//Miller Rabin primality test based on Wikipedia pseudocode
	public static boolean MillerRabin(BigInteger n, int k) {
		if (n.compareTo(ONE) == 0)
			return false;
		if (n.compareTo(THREE) < 0)
			return true;
		int s = 0;
		BigInteger d = n.subtract(ONE);
		
		//write n-1 as 2^s * d
		while (d.mod(TWO).equals(ZERO)) {
			s++;
			d = d.divide(TWO);
		}
		
		for (int i = 0; i < k; i++) {
			BigInteger a = uniformRandom(TWO, n.subtract(ONE));
			BigInteger x = a.modPow(d, n);//x = a^d mod n
			if (x.equals(ONE) || x.equals(n.subtract(ONE)))
				continue;
			int rcopy = 0;
			for (int r = 0; r < s; r++) {
				x = x.modPow(TWO, n);//x = x^2 mod n
				if (x.equals(ONE))
					return false;
				if (x.equals(n.subtract(ONE)))
					break;
				rcopy = r;
			}
			rcopy++;
			if (rcopy == s)
				return false;
		}
		return true;
	}

	//Method to generate random BigInteger found online
	private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
		Random rnd = new Random();
		BigInteger res;
		do {
			res = new BigInteger(top.bitLength(), rnd);
		} while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
		return res;
	}
	
	//Given a BigInteger, find the next BigInteger that is likely prime
	private static BigInteger findNextPrime(BigInteger n, int k){
		if(n.mod(TWO).compareTo(ZERO) == 0){
			n = n.add(ONE);
		}
		while(!MillerRabin(n,k)){
				n = n.add(TWO);
		}
		return n;
	}
	
	private static BigInteger extendedEuclidean()
	{
		//Start
		BigInteger x = ZERO;
		BigInteger y = ONE;
		BigInteger lastX = ONE;
		BigInteger lastY = ZERO;
		BigInteger temp;
		BigInteger pKey = publicKey;
		BigInteger tot = totient;
		BigInteger q;
		BigInteger r;
		
		while(pKey.compareTo(ZERO) != 0)
		{
			q = tot.divide(pKey);
			r = tot.mod(pKey);
			
			tot = pKey;
			pKey = r;
			
			temp = x;
			x = lastX.subtract(q.multiply(x));
			lastX = temp;
			
			temp = y;
			y = lastY.subtract(q.multiply(y));
			lastY = temp;
		}
		
		if(lastY.compareTo(ZERO) < 0){
			lastY = lastY.add(totient);
		}
		
		return lastY;
			
	}
	
	// RSA Decryption
    public static BigInteger decryption(BigInteger privKey, BigInteger cipherText, BigInteger ncopy){  
        
        // C^d in the decryption process
        while(privKey.compareTo(BigInteger.ZERO) > 0){    
            totalOfCipher = cipherText.multiply(cipherText);
            privKey = privKey.subtract(BigInteger.ONE);
        }
        // C^d mod n
        BigInteger plainTextAfter;
        plainTextAfter = totalOfCipher.mod(ncopy);
        System.out.println("The plaintext is " + plainTextAfter);
        return plainTextAfter;

    }
    
	// RSA Encryption
    public static BigInteger encryption(BigInteger pKey, BigInteger plainText, BigInteger ncopy){
        
        // M^e in the encryption process
        while(pKey.compareTo(BigInteger.ZERO) > 0 ){
            totalOfPlain = plainText.multiply(plainText);
            pKey = pKey.subtract(BigInteger.ONE);
        }
        
        // M^e mod n
        BigInteger cipherTextAfter;
        cipherTextAfter = totalOfPlain.mod(ncopy);
        System.out.println("The ciphertext is " + cipherTextAfter);
        return cipherTextAfter;
    }
	
    
    public static BigInteger RSA(BigInteger key, BigInteger n, BigInteger msg){
    	BigInteger output;
    	output = modExp(msg, key, n);
    	return output;
    }
    
    //Modular Exponentiation function for BigIntegers 
    public static BigInteger modExp(BigInteger base, BigInteger exp, BigInteger md){
        BigInteger result = BigInteger.ONE;
        base = base.mod(md);
        for (int i = 0; i < exp.bitLength(); ++i) {
        	if (exp.testBit(i)) {
        		result = result.multiply(base).mod(md);
        	}
        	base = base.multiply(base).mod(md);
        }
        return result;
    }
    
    public static void encryptString(String msg, BigInteger key, BigInteger n){
    	for(int i = 0; i < msg.length(); i++){
    		System.out.println(RSA(key, n, new BigInteger(Integer.toString(Character.getNumericValue(msg.charAt(i))))));
    	}
    }
    
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		/*
		System.out.println("Enter an 80+ digit number for p");
		String pString = scan.nextLine();
		p = findNextPrime(new BigInteger(pString),50);
		System.out.println("p = " + p);
		System.out.println("Enter an 80+ digit number for q");
		String qString = scan.nextLine();
		q = findNextPrime(new BigInteger(qString),20);
		System.out.println("q = " + q);
		n = p.multiply(q);
		//System.out.println("n = " + n);
		totient = p.subtract(ONE).multiply(q.subtract(ONE));
		//System.out.println("tot = " + totient);
		System.out.println("Enter a value for a public key");
		String pKey = scan.nextLine();
		publicKey = new BigInteger(pKey);
		privateKey = extendedEuclidean();
		System.out.println("Enter a two digit number to encrypt");
		String msg = scan.nextLine();
		BigInteger message = new BigInteger(msg);
		BigInteger cipher = RSA(publicKey, n, message);
		System.out.println(cipher);
		BigInteger plain = RSA(privateKey, n, cipher);
		System.out.println(plain);
		System.out.println(n);
		System.out.println(privateKey);
		*/
		/*
		System.out.println("Enter Public Key");
		String estr = scan.nextLine();
		System.out.println("Enter Public Key n");
		String nstr = scan.nextLine();
		BigInteger e = new BigInteger(estr);
		BigInteger n = new BigInteger(nstr);
		System.out.println("Enter Message to encrypt");
		String message = scan.nextLine();
		encryptString(message, e, n);
		*/
		String msg;
		String pkeystr;
		System.out.println("enter private Key");
		pkeystr = scan.nextLine();
		privateKey = new BigInteger(pkeystr);
		String nstr;
		System.out.println("enter n");
		nstr = scan.nextLine();
		n = new BigInteger(nstr);
		while(true){
			System.out.println("enter thing to decrypt");
			msg = scan.nextLine();
			System.out.println(RSA(privateKey, n, new BigInteger(msg)));
			
		}
	}
}
