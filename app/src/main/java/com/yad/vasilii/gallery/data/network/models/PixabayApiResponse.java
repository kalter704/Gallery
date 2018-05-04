
package com.yad.vasilii.gallery.data.network.models;

import com.google.gson.annotations.*;

import java.util.*;

public class PixabayApiResponse {

    @SerializedName("hits")
    private List<Hit> mHits;
    @SerializedName("total")
    private Long mTotal;
    @SerializedName("totalHits")
    private Long mTotalHits;

    public List<Hit> getHits() {
        return mHits;
    }

    public void setHits(List<Hit> hits) {
        mHits = hits;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    public Long getTotalHits() {
        return mTotalHits;
    }

    public void setTotalHits(Long totalHits) {
        mTotalHits = totalHits;
    }

}
