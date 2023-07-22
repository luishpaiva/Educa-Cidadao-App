package com.projects.educacidadaoapp.model;

public class ValidaSenha {

    public static boolean validaSenha(String senha) {

        int letra = 0, numero = 0, especial = 0;

        if (senha.length() < 8) {
            return false;
        } else {

            for (int i = 0; i < senha.length(); i++) {
                if (Character.isLetter(senha.charAt(i))) {
                    letra = 1;
                }
            }

            for (int j = 0; j < senha.length(); j++) {
                if (Character.isDigit(senha.charAt(j))) {
                    numero = 1;
                }
            }

            for (int k = 0; k < senha.length(); k++) {
                char c = senha.charAt(k);
                if (c >= 33 && c <= 46 || c == 64) {
                    especial = 1;
                }
            }

            return letra == 1 && numero == 1 && especial == 1;
        }

    }

}
