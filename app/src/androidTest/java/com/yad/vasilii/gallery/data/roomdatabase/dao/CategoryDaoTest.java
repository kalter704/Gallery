package com.yad.vasilii.gallery.data.roomdatabase.dao;

import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.test.*;

import org.junit.*;
import org.junit.runner.*;

import android.arch.persistence.room.*;
import android.support.test.*;
import android.support.test.runner.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CategoryDaoTest {

    private GalleryDatabase mDB;

    private CategoryDao mCategoryDao;

    @Before
    public void setUp() throws Exception {
        mDB = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                GalleryDatabase.class).build();
        mCategoryDao = mDB.categoryDao();
    }

    @After
    public void tearDown() throws Exception {
        mDB.close();
    }

    @Test
    public void insert_shouldReturnTheSameThatSaved() {
        int numberOfCategories = 10;
        List<Category> categoriesForInsert = CategoryUtils.categories(numberOfCategories);

        for (Category category : categoriesForInsert) {
            mCategoryDao.insert(category);
        }

        List<Category> resultCategories = mCategoryDao.getAll();

        assertEquals(numberOfCategories, resultCategories.size());
        assertListEquals(resultCategories, categoriesForInsert);
    }

    private void assertListEquals(List<Category> first, List<Category> second) {
        assertEquals(first.size(), second.size());
        for (int i = 0; i < first.size(); i++) {
            assertEquals(first.get(i).getId(), second.get(i).getId());
            assertEquals(first.get(i).getTitle(), second.get(i).getTitle());
        }
    }

    @Test
    public void delete_shouldReturnAllCategoriesWithoutDeleted() {
        int numberOfCategories = 10;
        List<Category> categoriesForInsert = CategoryUtils.categories(numberOfCategories);

        for (Category category : categoriesForInsert) {
            mCategoryDao.insert(category);
        }

        mCategoryDao.delete(categoriesForInsert.remove(2));

        List<Category> resultCategories = mCategoryDao.getAll();

        assertEquals(numberOfCategories - 1, resultCategories.size());
        assertListEquals(resultCategories, categoriesForInsert);
    }
}