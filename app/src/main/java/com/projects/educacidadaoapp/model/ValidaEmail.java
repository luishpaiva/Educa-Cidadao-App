package com.projects.educacidadaoapp.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaEmail {

    public static boolean validaEmail(String email) {

        boolean valido = false;

        if (email != null && email.length() > 0) {
            String expressao = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                valido = true;
            }
        }

        return valido;

    }

}
