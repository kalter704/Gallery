package com.yad.vasilii.gallery.data.roomdatabase.models;

import android.arch.persistence.room.*;

@Entity
public class CachedImage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "previewImageUrl")
    private String mPreviewImageUrl;

    @ColumnInfo(name = "largeImageUrl")
    private String mLargeImageUrl;

    @ColumnInfo(name = "query")
    private String mQuery;

    @ColumnInfo(name = "page")
    private int mPage;

    @ColumnInfo(name = "perPage")
    private int mPerPage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreviewImageUrl() {
        return mPreviewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        mPreviewImageUrl = previewImageUrl;
    }

    public String getLargeImageUrl() {
        return mLargeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        mLargeImageUrl = largeImageUrl;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public void setPerPage(int perPage) {
        mPerPage = perPage;
    }
}
