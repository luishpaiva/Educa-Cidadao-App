package com.projects.educacidadaoapp.model;

import java.security.*;
import java.math.*;

public class HashMD5 {

    public static String hashMD5(String senha) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(),0,senha.length());
        return new BigInteger(1, m.digest()).toString(16);
    }

}
