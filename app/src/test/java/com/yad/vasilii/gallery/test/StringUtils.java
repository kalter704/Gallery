package com.yad.vasilii.gallery.test;

import java.util.*;

public class StringUtils {

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static final String alphabet = upper + lower;

    public static String getRandomString() {
        return getRandomString(new Random().nextInt(50));
    }

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        char[] symbols = alphabet.toCharArray();
        for (int i = 0; i < length; i++) {
            builder.append(symbols[random.nextInt(symbols.length)]);
        }
        return builder.toString();
    }

}
