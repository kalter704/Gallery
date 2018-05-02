package com.yad.vasilii.gallery.test;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import java.util.*;

public class CategoryUtils {

    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static final String alphabet = upper + lower;

    private static String getRandomString() {
        return getRandomString(new Random().nextInt(50));
    }

    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        char[] symbols = alphabet.toCharArray();
        for (int i = 0; i < length; i++) {
            builder.append(symbols[random.nextInt(symbols.length)]);
        }
        return builder.toString();
    }

    public static List<Category> categories() {
        return Arrays.asList(createCategory(1, "Title1"), createCategory(2, "Title2"),
                createCategory(3, "Title3"));
    }

    public static List<Category> categories(int n) {
        List<Category> categories = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            categories.add(createCategory(i + 1, getRandomString()));
        }
        return categories;
    }

    public static Category createCategory(int id, String title) {
        Category cat = new Category();
        cat.setId(id);
        cat.setTitle(title);
        return cat;
    }

    public static Category createCategory(String title) {
        Category cat = new Category();
        cat.setTitle(title);
        return cat;
    }

}
