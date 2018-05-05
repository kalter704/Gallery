package com.yad.vasilii.gallery.data.roomdatabase.models;

import android.arch.persistence.room.*;

@Entity
public class TimeWhenCached {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private long mTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }
}
