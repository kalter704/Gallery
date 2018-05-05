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

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CachedImageDaoTest {

    private GalleryDatabase mDB;

    private CachedImageDao mCachedImageDao;

    @Before
    public void setUp() throws Exception {
        mDB = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                GalleryDatabase.class).build();
        mCachedImageDao = mDB.cachedImageDao();
    }

    @After
    public void tearDown() throws Exception {
        mDB.close();
    }

    @Test
    public void insert_shouldReturnTheSameThatInserted() {
        int numberOfImages = 15;
        String query = "temp";
        int page = 3;
        int perPage = 7;
        List<CachedImage> images = prepareImages(numberOfImages, query, page, perPage);

        mCachedImageDao.insertAll(images);

        List<CachedImage> result = mCachedImageDao.getImagesByQueryAndPageAndPerPage(query, page, perPage);

        assertEquals(numberOfImages, result.size());
        assertListEquals(images, result);
    }

    @Test
    public void delete_shouldDeleteAllThatInserted() {
        int numberOfImages = 15;
        String query = "temp";
        int page = 3;
        int perPage = 7;
        List<CachedImage> images = prepareImages(numberOfImages, query, page, perPage);

        mCachedImageDao.insertAll(images);

        mCachedImageDao.deleteAll();

        List<CachedImage> result = mCachedImageDao.getImagesByQueryAndPageAndPerPage(query, page, perPage);

        assertEquals(0, result.size());
    }

    private void assertListEquals(List<CachedImage> images1, List<CachedImage> images2) {
        assertEquals(images1.size(), images2.size());
        for (int i = 0; i < images1.size(); i++) {
            assertEquals(images1.get(i).getLargeImageUrl(), images2.get(i).getLargeImageUrl());
            assertEquals(images1.get(i).getPreviewImageUrl(), images2.get(i).getPreviewImageUrl());
            assertEquals(images1.get(i).getQuery(), images2.get(i).getQuery());
            assertEquals(images1.get(i).getPage(), images2.get(i).getPage());
            assertEquals(images1.get(i).getPerPage(), images2.get(i).getPerPage());
        }
    }

    private List<CachedImage> prepareImages(int numberOfImages, String query, int page, int perPage) {
        List<CachedImage> images = new ArrayList<>(numberOfImages);
        for (int i = 0; i < numberOfImages; i++) {
            CachedImage image = new CachedImage();
            image.setLargeImageUrl(StringUtils.getRandomString());
            image.setPreviewImageUrl(StringUtils.getRandomString());
            image.setQuery(query);
            image.setPage(page);
            image.setPerPage(perPage);
            images.add(image);
        }
        return images;
    }

}