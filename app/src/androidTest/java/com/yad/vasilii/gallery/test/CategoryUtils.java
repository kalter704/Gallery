package com.yad.vasilii.gallery.test;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import java.util.*;

public class CategoryUtils {

    public static List<Category> categories() {
        return Arrays.asList(createCategory(1, "Title1"), createCategory(2, "Title2"),
                createCategory(3, "Title3"));
    }

    public static List<Category> categories(int n) {
        List<Category> categories = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            categories.add(createCategory(i + 1, StringUtils.getRandomString()));
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
