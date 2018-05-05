package com.yad.vasilii.gallery.data.roomdatabase.dao;

import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import org.junit.*;

import android.arch.persistence.room.*;
import android.support.test.*;

import java.util.*;

import static junit.framework.Assert.*;

public class TimeWhenCachedDaoTest {

    private GalleryDatabase mDB;

    private TimeWhenCachedDao mTimeWhenCached;

    @Before
    public void setUp() throws Exception {
        mDB = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                GalleryDatabase.class).build();
        mTimeWhenCached = mDB.timeWhenCached();
    }

    @After
    public void tearDown() throws Exception {
        mDB.close();
    }

    @Test
    public void insert_shouldReturnTheSameThatInserted() {
        long time = 123456789L;
        TimeWhenCached timeWhenCached = new TimeWhenCached();
        timeWhenCached.setTime(time);

        mTimeWhenCached.insert(timeWhenCached);

        List<TimeWhenCached> result = mTimeWhenCached.getAll();

        assertEquals(1, result.size());
        assertEquals(time, result.get(0).getTime());
    }

    @Test
    public void delete_shouldDeleteAllThatInserted() {
        long time = 123456789L;
        TimeWhenCached timeWhenCached = new TimeWhenCached();
        timeWhenCached.setTime(time);

        mTimeWhenCached.insert(timeWhenCached);

        mTimeWhenCached.deleteAll();

        List<TimeWhenCached> result = mTimeWhenCached.getAll();

        assertEquals(0, result.size());
    }
}