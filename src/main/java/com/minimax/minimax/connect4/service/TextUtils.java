package com.minimax.minimax.connect4.service;

import java.util.List;
import java.util.Objects;

/**
 * Utilidades de texto sin estado.
 */
public final class TextUtils {

    private TextUtils() {
        // Evitar instanciación (regla PMD: UseUtilityClass)
    }

    /**
     * Une las cadenas no nulas con un guion '-'.
     *
     * @param parts lista de cadenas; null o vacía -> ""
     * @return texto unido
     */
    public static String joinWithDash(final List<String> parts) {
        if (parts == null || parts.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (final String part : parts) {
            if (part == null) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append('-');
            }
            sb.append(part);
        }
        return sb.toString();
    }

    /**
     * Cuenta letras [A-Za-z] en el texto.
     *
     * @param text texto de entrada; null se trata como ""
     * @return número de letras
     */
    public static int countLetters(final String text) {
        final String s = Objects.toString(text, "");
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                count++;
            }
        }
        return count;
    }
}