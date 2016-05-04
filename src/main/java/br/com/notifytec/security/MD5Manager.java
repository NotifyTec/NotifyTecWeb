package br.com.notifytec.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MD5Manager {

    public  static String generate(String text) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(text.getBytes(), 0, text.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Manager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
