package util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static String toMD5(String msg){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        }catch (Exception e){
            return "";
        }
    }
}
