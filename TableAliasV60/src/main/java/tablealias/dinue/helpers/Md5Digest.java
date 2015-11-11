/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablealias.dinue.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author INEGI
 */
public class Md5Digest {

    public static String digest(String message) throws NoSuchAlgorithmException {
        MessageDigest m = null;
        m = MessageDigest.getInstance("MD5");
        if (m != null) {
            m.update(message.getBytes(), 0, message.length());
            return new BigInteger(1, m.digest()).toString(16);
        } else {
            return null;
        }
    }
}
