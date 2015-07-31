package Interfaces;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
 
public class Encriptacion {
    
    public static String Encriptar(String texto) {
        String clave= "qualityinfosolutions"; 
        String nuevaclave = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] password = md.digest(clave.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(password, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            nuevaclave = new String(base64Bytes);
        } catch (Exception ex) {
        }
        return nuevaclave;
    }
    public static String Desencriptar(String texto) throws Exception {
        String clave= "qualityinfosolutions";
        String nuevaclave = "";
        try {
            byte[] message = Base64.decodeBase64(texto
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    .getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] Password = md.digest(clave.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(Password, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = decipher.doFinal(message);
            nuevaclave = new String(plainText, "UTF-8");
        } catch (Exception ex) {
        }
        return nuevaclave;
    }
}