package util;
 
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import exception.CryptoException;
 
/**
 * A utility class that encrypts or decrypts a file.
 *
 */
public class CryptoUtils {
	private static final String KEY = "LtlH#21?2sLtlH#2";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
 
    public static byte[] encrypt(String frase)
            throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] outputBytes = cipher.doFinal(frase.getBytes());
            
            return outputBytes;
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException ex) {
            throw new CryptoException("Error encrypting file", ex);
        }
    }
 
    public static byte[] decrypt(byte frase[])
            throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] outputBytes = cipher.doFinal(frase);
            
            return outputBytes;
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException ex) {
            throw new CryptoException("Error decrypting file", ex);
        }
    }
}